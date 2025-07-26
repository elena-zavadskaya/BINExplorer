package com.example.binexplorer.data.model

import com.squareup.moshi.Json

data class BinInfo(
    @field:Json(name = "scheme") val scheme: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "brand") val brand: String?,
    @field:Json(name = "prepaid") val prepaid: Boolean?,
    @field:Json(name = "country") val country: Country?,
    @field:Json(name = "bank") val bank: Bank?
)