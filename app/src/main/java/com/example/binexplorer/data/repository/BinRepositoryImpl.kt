package com.example.binexplorer.data.repository

import com.example.binexplorer.data.model.Bank as DataBank
import com.example.binexplorer.data.model.BinInfo as DataBinInfo
import com.example.binexplorer.data.model.Country as DataCountry
import com.example.binexplorer.data.remote.ApiClient
import com.example.binexplorer.domain.model.Bank
import com.example.binexplorer.domain.model.BinInfo
import com.example.binexplorer.domain.model.Country
import com.example.binexplorer.domain.repository.BinRepository
import com.example.binexplorer.domain.util.ResourceResult
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
) : BinRepository {

    override suspend fun getBinInfo(bin: String): ResourceResult<BinInfo> {
        return try {
            val response = apiClient.binApiService.getBinInfo(bin)
            ResourceResult.Success(response.toDomainModel())
        } catch (e: Exception) {
            ResourceResult.Error(e)
        }
    }

    private fun DataBinInfo.toDomainModel(): BinInfo {
        return BinInfo(
            scheme = scheme,
            type = type,
            brand = brand,
            prepaid = prepaid,
            country = country?.toDomainModel(),
            bank = bank?.toDomainModel()
        )
    }

    private fun DataCountry.toDomainModel(): Country {
        return Country(
            name = name,
            code = code,
            latitude = latitude,
            longitude = longitude
        )
    }

    private fun DataBank.toDomainModel(): Bank {
        return Bank(
            name = name,
            url = url,
            phone = phone,
            city = city
        )
    }
}