package com.example.binexplorer.data.repository

import com.example.binexplorer.data.local.history.BinHistoryDao
import com.example.binexplorer.data.local.history.BinHistoryEntity
import com.example.binexplorer.domain.model.BinHistoryItem
import com.example.binexplorer.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val binHistoryDao: BinHistoryDao
) : HistoryRepository {

    override suspend fun saveHistoryItem(bin: String, binInfo: BinHistoryItem) {
        binHistoryDao.insert(
            BinHistoryEntity(
                bin = bin,
                scheme = binInfo.scheme,
                type = binInfo.type,
                brand = binInfo.brand,
                bankName = binInfo.bankName,
                countryName = binInfo.countryName
            )
        )
    }

    override fun getAllHistory(): Flow<List<BinHistoryItem>> {
        return binHistoryDao.getAll().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun clearHistory() {
        binHistoryDao.clearAll()
    }

    private fun BinHistoryEntity.toDomainModel(): BinHistoryItem {
        return BinHistoryItem(
            id = id,
            bin = bin,
            scheme = scheme,
            type = type,
            brand = brand,
            bankName = bankName,
            countryName = countryName,
            timestamp = timestamp
        )
    }
}