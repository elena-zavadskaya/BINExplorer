package com.example.binexplorer.domain.repository

import com.example.binexplorer.domain.model.BinInfo
import com.example.binexplorer.domain.util.ResourceResult

interface BinRepository {
    suspend fun getBinInfo(bin: String): ResourceResult<BinInfo>
}