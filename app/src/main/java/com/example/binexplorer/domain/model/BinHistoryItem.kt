package com.example.binexplorer.domain.model

data class BinHistoryItem(
    val id: Long = 0,
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val bankName: String?,
    val countryName: String?,
    val timestamp: Long
)
