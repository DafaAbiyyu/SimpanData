package com.example.myrycyly

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataClass(
    @PrimaryKey
    val nis : Int,
    val nama : String,
    val kelas : String
)
