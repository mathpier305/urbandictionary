package com.example.urbandictionary.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Room
import com.google.gson.annotations.SerializedName


@Entity(tableName = Dfinition.DFINITION_TABLE_NAME)
class Dfinition(
    @SerializedName(DFINITION_ID)
    @PrimaryKey @ColumnInfo(name = DFINITION_ID)
    val defId: Long,
    @ColumnInfo(name = DFINITION_NAME)
    val word: String,
    @ColumnInfo(name = DEFINITION)
    val definition: String,
    @SerializedName(THUMB_UP)
    @ColumnInfo(name = THUMB_UP)
    val thumbUp: Int,
    @SerializedName(THUMB_DOWN)
    @ColumnInfo(name = THUMB_DOWN)
    val thumbDown: Int
) : Room() {


    companion object {
        const val DFINITION_TABLE_NAME = "dfinition"
        const val DFINITION_ID = "defid"
        const val DEFINITION = "definition"
        const val THUMB_UP = "thumbs_up"
        const val THUMB_DOWN = "thumbs_down"
        const val DFINITION_NAME = "word"
    }

    override fun toString(): String {
        return "Dfinition($defId, $word, $definition, $thumbUp, $thumbDown)"
    }
}