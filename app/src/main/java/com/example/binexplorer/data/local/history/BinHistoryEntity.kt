package com.example.binexplorer.data.local.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val bankName: String?,
    val countryName: String?,
    val timestamp: Long = System.currentTimeMillis()
)