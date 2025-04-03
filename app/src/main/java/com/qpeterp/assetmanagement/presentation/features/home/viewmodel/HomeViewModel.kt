package com.qpeterp.assetmanagement.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import com.qpeterp.assetmanagement.domain.model.stock.StockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _assets = MutableStateFlow<List<StockData>>(emptyList())
    val assets: StateFlow<List<StockData>> = _assets.asStateFlow()

    init {
        loadDummyData()
    }

    // TODO: api 연동 시, StockUseCase 구현 및 연동
    private fun loadDummyData() {
        _assets.value = listOf(
            StockData("TEST1", "stock", "테스트를 위한 종목1", 1, 10.05, "test security 1"),
            StockData("TEST2", "stock", "테스트를 위한 종목2", 1, 8.03, "test security 2"),
            StockData("TEST3", "stock", "테스트를 위한 종목3", 1, 6.5, "test security 3"),
            StockData("TEST4", "stock", "테스트를 위한 종목4", 1, 8.5, "test security 4"),
            StockData("TEST5", "bond", "테스트를 위한 종목5", 3, 9.5, "test security 5"),
            StockData("TEST6", "bond", "테스트를 위한 종목6", 1, 8.5, "test security 6"),
            StockData("TEST7", "bond", "테스트를 위한 종목7", 1, 13.42, "test security 7"),
            StockData("usd_cash", "etc", "현금", 1, 35.5, "cash")
        )
    }
}