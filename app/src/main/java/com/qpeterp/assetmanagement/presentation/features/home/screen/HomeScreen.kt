package com.qpeterp.assetmanagement.presentation.features.home.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.assetmanagement.application.MyApplication
import com.qpeterp.assetmanagement.domain.model.stock.StockData
import com.qpeterp.assetmanagement.presentation.features.home.viewmodel.HomeViewModel
import com.qpeterp.assetmanagement.presentation.root.navigation.NavGroup
import com.qpeterp.assetmanagement.presentation.utils.darkenColor

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val assets by viewModel.assets.collectAsState()
    val typeColorMap = viewModel.typeColorMap
    var lastType: String? = null

    val assetColors = mutableListOf<Color>()

    var prevType: String? = null
    var typeCount = 0

    assets.forEach { asset ->
        if (asset.type == prevType) {
            typeCount++
        } else {
            typeCount = 0
            prevType = asset.type
        }

        val baseColor = typeColorMap[asset.type] ?: Color.Gray
        val darkenFactor = 1f - (typeCount * 0.1f) // 점점 어두워짐

        assetColors.add(darkenColor(baseColor, darkenFactor))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 96.dp, 32.dp, 32.dp)
        ) {
            Text(
                text = "${MyApplication.prefs.userId}님 어서오세요.",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "AIM에서 안전한 투자 자산을 만들어보세요.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .fillMaxSize()
                .padding(32.dp, 64.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DonutChart(assets, assetColors) { assetType ->
                    navController.navigate("${NavGroup.Main.SECURITY}/$assetType")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    // TODO: 추후 enumClass 로 만들어서 자산 현황에 따라 메세지 변경
                    Text(
                        text = "장기투자에 적합한\n적극적인 자산배분",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 32.sp,
                    )
                    Column {
                        Row {
                            Text(
                                text = "'평생안전 은퇴자금'",
                                fontSize = 16.sp,
                                color = Color.White,
                            )
                            Text(
                                text = "에",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }

                        Text(
                            text = "최적화된 자산배분입니다.",
                            fontSize = 16.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
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
                            }, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    RatioItem(
                        label = asset.securitySymbol,
                        ratio = asset.ratio,
                        color = assetColors[assets.indexOf(asset)],
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
    var animatedRatio by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(ratio) {
        animate(
            initialValue = animatedRatio,
            targetValue = (ratio.toFloat() / 100f).coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        ) { value, _ ->
            animatedRatio = value
        }
    }

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
                    .fillMaxWidth(fraction = animatedRatio) // 애니메이션을 적용한 비율로 width를 설정
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

@Composable
fun DonutChart(
    assets: List<StockData>,
    colors: List<Color>,
    onArcClick: (String) -> Unit // 클릭된 아크의 type을 전달
) {
    val totalRatio = assets.sumOf { it.ratio }
    val angles = assets.map { (it.ratio / totalRatio * 360).toFloat() }
    val strokeWidth = 100f
    var currentAngle by remember { mutableFloatStateOf(0f) }

    // 전체 차트가 한 번에 그려지도록 애니메이션을 적용
    LaunchedEffect(assets) {
        animate(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        ) { value, _ ->
            currentAngle = value
        }
    }

    Box(
        modifier = Modifier.size(150.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    var startAngle = -90f
                    assets.forEachIndexed { index, asset ->
                        val arcRect = Rect(
                            Offset(strokeWidth / 2f, strokeWidth / 2f),
                            Size(size.width - strokeWidth, size.height - strokeWidth)
                        )
                        val endAngle = startAngle + angles[index]

                        if (isPointInArc(offset, arcRect, startAngle, endAngle)) {
                            onArcClick(asset.type) // 자산의 type을 전달
                        }
                        startAngle += angles[index]
                    }
                }
            }
        ) {
            val inset = strokeWidth / 2f
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)

            var startAngle = -90f
            assets.forEachIndexed { index, _ ->
                val sweepAngle = (angles[index] / angles.sum()) * currentAngle
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                    topLeft = Offset(inset, inset),
                    size = arcSize
                )
                startAngle += angles[index]
            }
        }
    }
}

fun isPointInArc(point: Offset, rect: Rect, startAngle: Float, endAngle: Float): Boolean {
    val dx = point.x - rect.center.x
    val dy = point.y - rect.center.y
    val distanceSquared = dx * dx + dy * dy
    val radiusSquared = rect.width / 2f * rect.width / 2f

    return distanceSquared <= radiusSquared &&
            startAngle <= Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toFloat() &&
            endAngle >= Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toFloat()
}