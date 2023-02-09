package com.example.myrycyly

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [DataClass::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun siswaDao(): SiswaDAO

    companion object{
        @Volatile private var instance : AppDatabase? = null
        private val lock = Any()

        operator fun invoke (context: Context) = instance?: synchronized(lock){
            instance?: DatabaseBuilder(context).also{
                instance = it
            }
        }

        private fun DatabaseBuilder(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "das.db"
        ).build()
    }

}
