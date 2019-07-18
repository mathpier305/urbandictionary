package com.example.urbandictionary.network

interface ResponseCallback<T> {

    fun onSuccess(result: T?)

    fun onFailure(error: String)
}