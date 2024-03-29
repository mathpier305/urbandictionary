package com.example.urbandictionary.view.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.urbandictionary.data.entities.Dfinition
import com.example.urbandictionary.data.repository.DictionaryRepository

class SearchWordViewModel(application: Application) : AndroidViewModel(application) {

    val repository: DictionaryRepository = DictionaryRepository(application.applicationContext)
    var word: String = ""
    var sortOrder: Int = 0

    val isLoading: LiveData<Boolean> = isLoading()

    fun isLoading(): MutableLiveData<Boolean> {
        return repository.isLoading
    }

    fun getDefinitions(word: String, sortOrder: Int): LiveData<List<Dfinition>> {
        this.word = word
        this.sortOrder = sortOrder
        return repository.getDefinitions(word, sortOrder)
    }

    fun onSearchClicked(word: String) {
        this.word = word
    }
}