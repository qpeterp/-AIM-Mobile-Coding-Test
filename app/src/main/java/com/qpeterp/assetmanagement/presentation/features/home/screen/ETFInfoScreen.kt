package com.qpeterp.assetmanagement.presentation.features.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.qpeterp.assetmanagement.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EtfInfoScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "ETF란?",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ETF(Exchange Traded Fund)는 상장지수펀드로, 특정 지수나 자산을 추종하는 펀드를 주식처럼 거래할 수 있는 금융상품입니다.\n\n" +
                        "예를 들어, KOSPI200 ETF는 KOSPI200 지수를 그대로 따라가며, 주식처럼 자유롭게 매수와 매도가 가능합니다.\n\n" +
                        "ETF의 장점은 분산투자, 낮은 수수료, 실시간 거래 가능성 등이 있으며, 다양한 자산군(주식, 채권, 원자재 등)에 접근할 수 있습니다.",
                fontSize = 18.sp,
                lineHeight = 28.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4FF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("예시: 타이거 미국나스닥100 ETF", fontWeight = FontWeight.SemiBold)
                    Text("→ 나스닥100 지수를 따라가는 ETF. 미국 기술주에 간접 투자 가능.")
                }
            }
        }
    }
}