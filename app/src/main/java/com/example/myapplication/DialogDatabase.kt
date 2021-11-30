package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DialogData::class], version = 1)
abstract class DialogDatabase: RoomDatabase() {
    abstract fun dataDialog():DataDailogDao

}
