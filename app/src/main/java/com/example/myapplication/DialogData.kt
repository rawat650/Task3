package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "To_Do" )
data class DialogData(
    @PrimaryKey( autoGenerate = true)
    var id:Int,
    @ColumnInfo(name ="title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: String)
