package com.example.binexplorer.domain.interactor

import com.example.binexplorer.domain.model.BinInfo
import com.example.binexplorer.domain.util.ResourceResult

interface BinInteractor {
    suspend fun getBinInfo(bin: String): ResourceResult<BinInfo>
}