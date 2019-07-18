package com.example.urbandictionary.network

import com.example.urbandictionary.data.entities.DefinitionList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class DictionaryRestApi {

    companion object {
        private const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
    }

    private val dictionaryApi: DictionaryApi

    init {
        val loginInterceptor = HttpLoggingInterceptor()
        loginInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loginInterceptor).build()
        val gson: Gson = GsonBuilder().create()


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        dictionaryApi = retrofit.create(DictionaryApi::class.java)
    }

    fun getDefinitionList(word: String): Call<DefinitionList> {
        return dictionaryApi.getDefinitionList(word)
    }

    interface DictionaryApi {

        @Headers(
            "X-RapidAPI-Key: 25dd6bc839mshe840bb72f19f5d7p1503a3jsna9e55c8bb7e4"
        )
        @GET("define")
        fun getDefinitionList(@Query("term") term: String): Call<DefinitionList>
    }

}