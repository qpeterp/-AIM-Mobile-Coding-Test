package com.qpeterp.assetmanagement.data.data.home.stock

import kotlinx.serialization.Serializable

@Serializable
data class StockData(
    val securitySymbol: String,
    val type: String,
    val securityDescription: String,
    val quantity: Int,
    val ratio: Double,
    val securityName: String
)
