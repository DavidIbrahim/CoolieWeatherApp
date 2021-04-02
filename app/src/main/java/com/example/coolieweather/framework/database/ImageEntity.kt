package com.example.coolieweather.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_saved")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imagePath: String,
    val savedTime: Long,
)