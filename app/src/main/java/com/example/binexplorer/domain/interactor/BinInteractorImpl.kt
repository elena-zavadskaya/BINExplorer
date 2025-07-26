package com.example.binexplorer.domain.interactor

import com.example.binexplorer.domain.model.BinInfo
import com.example.binexplorer.domain.repository.BinRepository
import com.example.binexplorer.domain.util.ResourceResult
import javax.inject.Inject

class BinInteractorImpl @Inject constructor(
    private val repository: BinRepository
) : BinInteractor {
    override suspend fun getBinInfo(bin: String): ResourceResult<BinInfo> {
        return repository.getBinInfo(bin)
    }
}