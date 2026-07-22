package com.project.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example")
data class ExampleEntity(
    @PrimaryKey val id: String?
)
