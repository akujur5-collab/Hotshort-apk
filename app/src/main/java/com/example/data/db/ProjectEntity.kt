package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val previewPath: String, // local file path to saved PNG
    val timestamp: Long = System.currentTimeMillis(),
    val textCount: Int = 1
)
