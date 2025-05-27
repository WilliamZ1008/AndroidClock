package com.example.androidplayground

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 时钟界面组件
 * 
 * 该组件实现了：
 * 1. 数字时钟显示
 * 2. 模拟时钟显示
 * 3. 实时时间更新
 * 4. 平滑的动画效果
 */
@Composable
@Preview
fun ClockScreen() {
    // 当前时间状态
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    
    // 创建无限动画过渡
    val infiniteTransition = rememberInfiniteTransition(label = "clock")
    
    // 获取主题颜色
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    
    // 秒针动画
    val secondRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "second"
    )

    // 每秒更新时间
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = LocalTime.now()
        }
    }

    // 主布局
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 数字时钟显示
        Text(
            text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            style = MaterialTheme.typography.headlineLarge,
            color = primaryColor
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 模拟时钟容器
        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // 绘制时钟
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.width.coerceAtMost(size.height) / 2
                
                // 绘制表盘
                drawCircle(
                    color = surfaceColor,
                    radius = radius,
                    center = center
                )
                
                // 绘制12小时刻度
                for (i in 0..11) {
                    val angle = i * 30f
                    val startRadius = radius * 0.9f
                    val endRadius = radius
                    val startX = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * startRadius
                    val startY = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * startRadius
                    val endX = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * endRadius
                    val endY = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * endRadius
                    
                    drawLine(
                        color = onSurfaceColor,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 4f,
                        cap = StrokeCap.Round
                    )
                }
                
                // 绘制时针
                rotate(currentTime.hour * 30f + currentTime.minute * 0.5f) {
                    drawLine(
                        color = primaryColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.5f),
                        strokeWidth = 8f,
                        cap = StrokeCap.Round
                    )
                }
                
                // 绘制分针
                rotate(currentTime.minute * 6f) {
                    drawLine(
                        color = secondaryColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.7f),
                        strokeWidth = 6f,
                        cap = StrokeCap.Round
                    )
                }
                
                // 绘制秒针
                rotate(secondRotation) {
                    drawLine(
                        color = tertiaryColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.8f),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round
                    )
                }
                
                // 绘制中心点
                drawCircle(
                    color = primaryColor,
                    radius = 8f,
                    center = center
                )
            }
        }
    }
} 