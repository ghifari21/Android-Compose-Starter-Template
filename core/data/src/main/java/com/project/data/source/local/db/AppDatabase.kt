package com.project.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.data.source.local.db.dao.ExampleDao
import com.project.model.entity.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}
