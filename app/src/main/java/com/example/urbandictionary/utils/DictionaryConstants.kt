package com.example.urbandictionary.utils

class DictionaryConstants {
    companion object {
        const val DEFAULT_SORTING_KEY = 0
        private const val DEFAULT_SORTING_VALUE = "Default"
        const val THUMB_UP_SORTING_KEY = 1
        private const val THUMB_UP_SORTING_VALUE = "Up Desc Thumbs"
        const val THUMB_DOWN_SORTING_KEY = 2
        private const val THUMB_DOWN_SORTING_VALUE = "Down Desc Thumb"

        val optionList= mapOf(
            DEFAULT_SORTING_KEY to DEFAULT_SORTING_VALUE,
            THUMB_UP_SORTING_KEY to THUMB_UP_SORTING_VALUE,
            THUMB_DOWN_SORTING_KEY to THUMB_DOWN_SORTING_VALUE)

    }

}