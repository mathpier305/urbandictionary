package com.example.urbandictionary.view.adapter

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class SortingSpinnerAdapter(private val context: Context) : AdapterView.OnItemSelectedListener{
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val list_of_items = arrayOf("Default", "Up Desc Thumb", "Down Desc Thumb")
    }

    fun setAdapter() {
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(context, R.layout.simple_spinner_item, list_of_items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
    }

    inner class Spinner

}