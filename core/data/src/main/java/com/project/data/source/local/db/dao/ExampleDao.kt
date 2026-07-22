package com.project.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.model.entity.ExampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExampleDao {
    @Query("SELECT * FROM example")
    fun getExample(): Flow<List<ExampleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExample(example: ExampleEntity)

    @Delete
    suspend fun deleteExample(example: ExampleEntity)
}
