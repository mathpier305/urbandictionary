package com.example.urbandictionary.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.urbandictionary.data.DictionaryDatabase
import com.example.urbandictionary.data.dao.DictionaryDao
import com.example.urbandictionary.data.entities.DefinitionList
import com.example.urbandictionary.data.entities.Dfinition
import com.example.urbandictionary.network.ResponseCallback
import com.example.urbandictionary.network.service.DictionaryService
import com.example.urbandictionary.utils.DictionaryConstants
import org.jetbrains.anko.doAsync

class DictionaryRepository(context: Context) {

    private val dictionaryService: DictionaryService = DictionaryService()
    private val dictionaryDatabase: DictionaryDatabase = DictionaryDatabase.getDatabase(context = context)
    private val dictionaryDao: DictionaryDao
    private var definitionList: LiveData<List<Dfinition>>? = null
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        dictionaryDao = dictionaryDatabase.getDictionary()
        isLoading.value = false
    }

    companion object {
        const val TAG = "DictionaryRepository"
    }

    fun getDefinitions(word: String, sortOrder: Int) : LiveData<List<Dfinition>> {
        if(hasDefinition()){
            fetchDefinition(word)
        }

        when(sortOrder) {
            DictionaryConstants.DEFAULT_SORTING_KEY -> definitionList = getDefinition(word)
            DictionaryConstants.THUMB_UP_SORTING_KEY -> definitionList = getThumbUpDescDefinition(word)
            DictionaryConstants.THUMB_DOWN_SORTING_KEY -> definitionList = getThumbDownDescDefinition(word)
       }
       return definitionList!!
    }

    fun hasDefinition(): Boolean {
        return definitionList?.value.isNullOrEmpty()
    }

    private fun getDefinition(word: String): LiveData<List<Dfinition>> {
        definitionList = dictionaryDao.getDefinition(word)
        return definitionList!!
    }

    private fun getThumbDownDescDefinition(word: String) : LiveData<List<Dfinition>>{
        definitionList = dictionaryDao.getThumbsDownDefinition(word)
        return definitionList!!
    }

    private fun getThumbUpDescDefinition(word: String) : LiveData<List<Dfinition>>{
        definitionList = dictionaryDao.getThumbsUpDefinition(word)
        return definitionList!!
    }

    private fun fetchDefinition(word: String) {
        isLoading.value = true
        dictionaryService.fetchDefinition(word, object : ResponseCallback<DefinitionList> {
            override fun onSuccess(result: DefinitionList?) {
                Log.d(TAG, result.toString())
                if (result != null) {
                    populateDefinition(result)
                }
                isLoading.value = false
            }

            override fun onFailure(error: String) {
                Log.d(TAG, error)
                isLoading.value = false
            }
        })
    }

    private fun populateDefinition(definitionList: DefinitionList) {
        doAsync {
            definitionList.list.forEach { definition ->
                dictionaryDao.insertDefinition(definition)
            }
        }
    }
}