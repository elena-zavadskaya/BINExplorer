package com.example.binexplorer.data.model

import com.squareup.moshi.Json

data class Bank(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "phone") val phone: String?,
    @field:Json(name = "city") val city: String?
)