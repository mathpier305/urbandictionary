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
import com.example.urbandictionary.utils.DictionaryConstants.Companion.DEFAULT_SORTING_KEY
import com.example.urbandictionary.view.adapter.DictionaryAdapter
import com.example.urbandictionary.view.viewmodel.SearchWordViewModel
import kotlinx.android.synthetic.main.fragment_search_words.*

class SearchWordFragment : Fragment() {

    private lateinit var fragmentSearchWord: FragmentSearchWordsBinding
    private lateinit var searchWordViewModel: SearchWordViewModel
    private lateinit var inputWord: String
    private lateinit var dictionaryAdapter: DictionaryAdapter
    var sortOrder: Int = DEFAULT_SORTING_KEY

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSearchWord = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_words, container, false
        )
        return fragmentSearchWord.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchWordViewModel = ViewModelProviders.of(this).get(SearchWordViewModel::class.java)
        setEditTextAction()

        dictionaryAdapter = DictionaryAdapter(context!!)
        fragmentSearchWord.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dictionaryAdapter
        }
        setSpinner()

        searchWordViewModel.isLoading().observe(this, Observer<Boolean> { isLoading ->
            if (isLoading!!) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun setEditTextAction() {
        fragmentSearchWord.inputText.setOnEditorActionListener(object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return true
                }
                return false
            }
        })
    }

    private fun setSpinner() {
        val spinner = fragmentSearchWord.spinner
        val listOfItems = arrayOf("Default", "Up Desc Thumbs", "Down Desc Thumb")
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOfItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = arrayAdapter
        fragmentSearchWord.spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val a = parent!!.getItemAtPosition(position).toString()
                Toast.makeText(this@SearchWordFragment.context, a, Toast.LENGTH_SHORT).show()
                sortOrder = position
                performSearch()
            }
        })
    }

    private fun performSearch() {
        inputWord = fragmentSearchWord.inputText.text.toString()

        if (!inputWord.isEmpty()) {
            observeDefinition()
        }
        dismissKeyboard()
    }

    private fun observeDefinition() {
        searchWordViewModel.getDefinitions(inputWord, sortOrder).observe(this@SearchWordFragment,
            Observer<List<Dfinition>> { definitions ->
                if (definitions!!.isNotEmpty()) {
                    updateView(inputWord)
                    dictionaryAdapter.setDefinitionList(definitions)
                }
            })
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
        fragmentSearchWord.definedWord.text = word
    }


}