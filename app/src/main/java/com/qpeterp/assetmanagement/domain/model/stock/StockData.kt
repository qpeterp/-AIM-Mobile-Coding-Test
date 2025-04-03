package com.qpeterp.assetmanagement.domain.model.stock

data class StockData(
    val securitySymbol: String,
    val type: String,
    val securityDescription: String,
    val quantity: Int,
    val ratio: Double,
    val securityName: String
)
