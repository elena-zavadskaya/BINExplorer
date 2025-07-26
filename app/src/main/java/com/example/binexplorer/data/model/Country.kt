package com.example.binexplorer.data.model

import com.squareup.moshi.Json

data class Country(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "alpha2") val code: String?,
    @field:Json(name = "latitude") val latitude: Double?,
    @field:Json(name = "longitude") val longitude: Double?
)