package com.example.binexplorer.domain.interactor

import com.example.binexplorer.domain.model.BinHistoryItem
import com.example.binexplorer.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryInteractor @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend fun saveHistory(bin: String, binInfo: BinHistoryItem) {
        historyRepository.saveHistoryItem(bin, binInfo)
    }

    fun getAllHistory(): Flow<List<BinHistoryItem>> {
        return historyRepository.getAllHistory()
    }

    suspend fun clearHistory() {
        historyRepository.clearHistory()
    }
}