package com.qpeterp.assetmanagement.data.data.home.stock

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockListData(
    @SerialName("asset_list") val assetList: List<StockData>
)
