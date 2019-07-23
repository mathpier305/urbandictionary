package com.example.urbandictionary.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.urbandictionary.R
import com.example.urbandictionary.data.entities.Dfinition
import com.example.urbandictionary.databinding.FragmentSearchWordsBinding
import com.example.urbandictionary.utils.DictionaryConstants
import com.example.urbandictionary.view.adapter.DictionaryAdapter
import com.example.urbandictionary.view.listener.EditTextListener
import com.example.urbandictionary.view.viewmodel.SearchWordViewModel
import kotlinx.android.synthetic.main.fragment_search_words.*

class SearchWordFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentSearchWordsBinding
    private lateinit var searchWordViewModel: SearchWordViewModel
    private lateinit var dictionaryAdapter: DictionaryAdapter
    var inputText: String = ""
    var definitions: List<Dfinition> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_words, container, false
        )
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchWordViewModel = ViewModelProviders.of(this).get(SearchWordViewModel::class.java)
        viewDataBinding.viewmodel = searchWordViewModel
        setupEditText()

        dictionaryAdapter = DictionaryAdapter(context!!)
        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dictionaryAdapter
        }
        setSpinner()

        if(savedInstanceState != null){
            inputText = searchWordViewModel.word
            performSearch()
        }
    }

    private fun setupEditText() {

        viewDataBinding.inputText.setOnEditorActionListener(object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewDataBinding.listener = object: EditTextListener {
                        override fun onSearchClicked() {
                            searchWordViewModel.onSearchClicked(v?.text.toString())
                        }
                    }


                    inputText = v?.text.toString()
                    searchWordViewModel.word = inputText
                    performSearch()
                    return true
                }
                return false
            }
        })
    }

    private fun setSpinner() {
        val spinner = viewDataBinding.spinner
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_item,
                DictionaryConstants.optionList.values.toList()
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = arrayAdapter
        viewDataBinding.spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val a = parent!!.getItemAtPosition(position).toString()
                Toast.makeText(this@SearchWordFragment.context, a, Toast.LENGTH_SHORT).show()
                searchWordViewModel.sortOrder = position
                if (searchWordViewModel.sortOrder != 0) {
                    performSearch()
                }
            }
        })
    }

    private fun performSearch() {
        if (!inputText.isEmpty()) {
            observeDefinition()
        }
        dismissKeyboard()
    }

    private fun observeDefinition() {
        searchWordViewModel.getDefinitions(inputText, searchWordViewModel.sortOrder).observe(this@SearchWordFragment,
            Observer<List<Dfinition>> { definitions ->
                if (definitions!!.isNotEmpty()) {
                    this.definitions = definitions
                    updateViews()
                }
            })
    }

    private fun updateViews() {
        updateView(inputText)
        dictionaryAdapter.setDefinitionList(definitions)
    }

    private fun dismissKeyboard() {
        val inputManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun updateView(word: String) {
        viewDataBinding.definedWord.text = word
    }

}