package com.qpeterp.assetmanagement.presentation.features.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.assetmanagement.R
import com.qpeterp.assetmanagement.presentation.features.home.viewmodel.HomeViewModel
import com.qpeterp.assetmanagement.presentation.root.navigation.NavGroup
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(
    navController: NavController,
    assetType: String? = "불러오지 못함.",
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val assets by viewModel.assets.collectAsState()
    val filteredAssets = assets.filter { it.type == assetType }
    val primaryColor = viewModel.getColorForType(assetType)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "뒤로가기"
                    )
                }
            },
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(primaryColor)
                        .border(1.dp, Color.White, RoundedCornerShape(100.dp))
                        .clickable { navController.navigate(NavGroup.Main.ETF_INFO) }
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("ETF란?", color = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = primaryColor,
                navigationIconContentColor = Color.White,
                titleContentColor = primaryColor
            )
        )
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(primaryColor)
                .padding(16.dp, 32.dp),
        ) {
            Text(
                text = when (assetType) {
                    "stock" -> "주식형 자산"
                    "bond" -> "채권형 자산"
                    "etc" -> "기타 자산"
                    else -> "기타 자산"
                },
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "각 ETF 종목별 기본 정보,\n보유 수량 정보입니다.",
                fontSize = 20.sp, color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .weight(7f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp, 32.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .offset(y = (-64).dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    filteredAssets,
                    key = { _, item -> item.securityName }
                ) { index, filteredAsset ->
                    var isVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(index * 100L) // 등장 지연
                        isVisible = true
                    }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(300)) +
                                slideInVertically(
                                    animationSpec = tween(300),
                                    initialOffsetY = { it / 2 }
                                )
                    ) {
                        SecurityCard(
                            securityName = filteredAsset.securityName,
                            securityDescription = filteredAsset.securityDescription,
                            quantity = filteredAsset.quantity,
                            primaryColor = primaryColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SecurityCard(
    securityName: String,
    quantity: Int,
    securityDescription: String,
    primaryColor: Color,
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp, 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = securityName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = securityDescription,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    fontSize = 16.sp
                )
            }
            Text(
                text = "${quantity}주",
                color = primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
        }
    }
}

