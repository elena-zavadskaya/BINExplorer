package com.example.binexplorer.domain.repository

import com.example.binexplorer.domain.model.BinHistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun saveHistoryItem(bin: String, binInfo: BinHistoryItem)
    fun getAllHistory(): Flow<List<BinHistoryItem>>
    suspend fun clearHistory()
}