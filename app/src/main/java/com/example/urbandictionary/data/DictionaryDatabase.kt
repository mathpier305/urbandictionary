package com.example.urbandictionary.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.urbandictionary.data.dao.DictionaryDao
import com.example.urbandictionary.data.entities.Dfinition

@Database(entities = [Dfinition::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun getDictionary(): DictionaryDao

    companion object {
        private const val DATABASE_NAME = "definition.db"

        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}