package com.example.urbandictionary.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.urbandictionary.data.entities.Dfinition

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM ${Dfinition.DFINITION_TABLE_NAME} WHERE ${Dfinition.DFINITION_NAME} LIKE :word ")
    fun getDefinition(word: String): LiveData<List<Dfinition>>

    @Query("SELECT * FROM ${Dfinition.DFINITION_TABLE_NAME} WHERE ${Dfinition.DFINITION_NAME} LIKE :word  ORDER BY ${Dfinition.THUMB_UP} DESC")
    fun getThumbsUpDefinition(word: String): LiveData<List<Dfinition>>

    @Query("SELECT * FROM ${Dfinition.DFINITION_TABLE_NAME} WHERE ${Dfinition.DFINITION_NAME} LIKE :word  ORDER BY ${Dfinition.THUMB_DOWN} DESC")
    fun getThumbsDownDefinition(word: String): LiveData<List<Dfinition>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDefinition(definition: Dfinition)

    @Update
    fun update(definition: Dfinition)
}