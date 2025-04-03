package com.qpeterp.assetmanagement.presentation.features.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.assetmanagement.application.MyApplication
import com.qpeterp.assetmanagement.presentation.features.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val assets by viewModel.assets.collectAsState()
    var lastType: String? = null

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 64.dp)
        ) {
            Text(text = "${MyApplication.prefs.userId}님 어서오세요.")
        }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .fillMaxSize()
                .padding(32.dp, 0.dp)
        ) {
            LazyColumn {
                items(assets) { asset ->
                    if (lastType != asset.type) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = when (asset.type) {
                                "stock" -> "주식형 자산"
                                "bond" -> "채권형 자산"
                                "etc" -> "기타 자산"
                                else -> "기타 자산"
                            },
                            fontSize = 12.sp,
                            color = Color(0xFF747474)
                        )
                    }
                    RatioItem(
                        label = asset.securitySymbol,
                        ratio = asset.ratio,
                        color = Color.DarkGray
                    )
                    lastType = asset.type
                }
            }
        }
    }
}

@Composable
fun RatioItem(
    label: String,
    ratio: Double,
    color: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label, modifier = Modifier.weight(2f), color = Color.LightGray, fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .weight(6f)
                .height(6.dp)
                .padding(32.dp, 0.dp, 0.dp, 0.dp)
                .background(Color(0xFF242B35), shape = CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = (ratio.toFloat() / 100f).coerceIn(0f, 1f))
                    .height(12.dp)
                    .background(color, shape = CircleShape)
            )
        }
        Text(
            text = "${ratio}%",
            color = color,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            fontSize = 16.sp
        )
    }
}