package com.bignerdranch.android.coursevalute

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Daoz {
@Query("SELECT * FROM `valute-list`")
fun getValute(): List<Valute>

@Query("SELECT count(*) FROM `valute-list`")
fun getCount(): Int

@Update fun update(inlist: List<Valute>)

@Insert fun insert(inlist: List<Valute>)
}