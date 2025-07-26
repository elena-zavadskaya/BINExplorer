package com.example.binexplorer.domain.interactor

import com.example.binexplorer.domain.model.BinHistoryItem
import com.example.binexplorer.domain.model.BinInfo
import com.example.binexplorer.domain.repository.BinRepository
import com.example.binexplorer.domain.util.ResourceResult
import javax.inject.Inject

class BinInteractorImpl @Inject constructor(
    private val repository: BinRepository,
    private val historyInteractor: HistoryInteractor
) : BinInteractor {
    override suspend fun getBinInfo(bin: String): ResourceResult<BinInfo> {
        val result = repository.getBinInfo(bin)

        if (result is ResourceResult.Success) {
            historyInteractor.saveHistory(bin, result.data.toHistoryItem())
        }

        return result
    }

    private fun BinInfo.toHistoryItem(): BinHistoryItem {
        return BinHistoryItem(
            bin = "", // Будет заполнено позже
            scheme = scheme,
            type = type,
            brand = brand,
            bankName = bank?.name,
            countryName = country?.name,
            timestamp = System.currentTimeMillis()
        )
    }
}