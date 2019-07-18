package com.example.urbandictionary.network.service

import com.example.urbandictionary.data.entities.DefinitionList
import com.example.urbandictionary.network.DictionaryRestApi
import com.example.urbandictionary.network.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryService(private val dictionaryApi: DictionaryRestApi = DictionaryRestApi()) {

    fun fetchDefinition(word: String, responseCallback: ResponseCallback<DefinitionList>) {
        val call = dictionaryApi.getDefinitionList(word)
        call.enqueue(object : Callback<DefinitionList> {
            override fun onFailure(call: Call<DefinitionList>, t: Throwable) {
                responseCallback.onFailure("call failed $t")
            }

            override fun onResponse(call: Call<DefinitionList>, response: Response<DefinitionList>) {
                if (response.isSuccessful) {
                    responseCallback.onSuccess(response.body())
                } else {
                    responseCallback.onFailure("Error Failed : ${response.errorBody()}")
                }
            }
        })
    }
}