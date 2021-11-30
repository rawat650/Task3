package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDailogDao {

     @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(dialogData: DialogData)

    @Query("Select * from To_Do ")
    fun getData():List<DialogData>

}
