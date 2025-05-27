package com.example.androidplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

/**
 * 时钟应用的主活动
 * 
 * 该活动负责：
 * 1. 设置应用的主题
 * 2. 初始化根布局
 * 3. 加载时钟界面
 */
class MainActivity : ComponentActivity() {
    /**
     * 活动创建时的回调
     * 
     * @param savedInstanceState 保存的实例状态，用于活动重建
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 使用 Material Design 3 主题
            MaterialTheme {
                // 创建全屏表面
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 加载时钟界面
                    ClockScreen()
                }
            }
        }
    }
}