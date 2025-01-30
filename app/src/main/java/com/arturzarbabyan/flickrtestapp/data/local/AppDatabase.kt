package com.arturzarbabyan.flickrtestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arturzarbabyan.flickrtestapp.domain.model.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}