package com.example.urbandictionary.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.urbandictionary.R
import com.example.urbandictionary.view.fragments.SearchWordFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchWordFragment = SearchWordFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, searchWordFragment)
            .commit()
    }

}
