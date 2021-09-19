package com.bignerdranch.android.coursevalute

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Valute::class], version =1)
abstract class AppDatabase: RoomDatabase(){
abstract fun Daoz(): Daoz
}