package com.example.simplemapofjapan.ui

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MapOfJapan(
    modifier: Modifier = Modifier,
    viewModel: MapOfJapanViewModel = viewModel()
){
    val mapUiState by viewModel.mapUiState.collectAsState()
    var mapOffset by remember { mutableStateOf(Offset.Zero) }
    var mapScale by remember { mutableStateOf(1f) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ){
        val displayWidth = maxWidth.value // コンポーザブルの最大幅 (dp)
        val displayHeight = maxHeight.value // コンポーザブルの最大高さ (dp)

        // dp->px
        val density = LocalDensity.current.density
        val displayWidthPx = displayWidth * density
        val displayHeightPx = displayHeight * density

        val boxHeight = displayHeightPx / 18
        val boxWidth = displayWidthPx / 17

        val prefectureList = viewModel.prefectures.map { (x, y, width, height, name, color) ->
            Prefecture(
                x * boxWidth,
                y * boxHeight,
                width * boxWidth,
                height * boxHeight,
                name,
                color
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit){
                    detectTransformGestures { _, pan, zoom ,_->
                        // パン操作で地図を移動
                        mapScale = (mapScale * zoom).coerceIn(1f, 2f) // ズームの範囲を制限
                        mapOffset = Offset(
                            (mapOffset.x + pan.x).coerceIn(-(200f + 17 * boxWidth * (mapScale - 1)), 0f),
                            (mapOffset.y + pan.y).coerceIn(-(200f + 18 * boxHeight * (mapScale - 1)), 0f)
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // タップ位置を補正するためにoffsetを引く
                        val adjustedTapOffset = offset.copy(
                            x = offset.x - mapOffset.x,
                            y = offset.y - mapOffset.y
                        )
                        // タップされた場所がどの四角形に当たるかを判定
                        prefectureList.forEach { (x, y, width, height, name) ->
                            Log.d("JapanMap", " $name : $x : ")

                            if (adjustedTapOffset.x in x * mapScale..(x + width) * mapScale && adjustedTapOffset.y in y* mapScale..(y + height) * mapScale) {
                                // クリックされた四角形があった場合
                                Log.d("JapanMap", "Clicked on: $name")
                                viewModel.prefectureClicked(name)
                            }
                        }
                    }
                }
        ) {
            // 都道府県ごとに四角形を描画
            prefectureList.forEach { (x, y, width, height, name, rectColor) ->
                // 四角形を描く
                drawRect(
                    color = rectColor, // 四角形の色
                    topLeft = Offset(x * mapScale + mapOffset.x, y * mapScale + mapOffset.y),
                    size = Size(width * mapScale, height * mapScale),
                )

                // 外枠を描く
                drawRect(
                    color = Color.White, // 四角形の色
                    topLeft = Offset(x * mapScale + mapOffset.x, y * mapScale + mapOffset.y),
                    size = Size(width * mapScale, height * mapScale),
                    style = Stroke(width = 2f) // 枠線の太さを指定
                )

                // 四角形内に都道府県名を表示
                drawIntoCanvas { canvas ->
                    val textPaint = Paint().apply {
                        color = Color.Black.toArgb() // ColorをIntに変換
                        textSize = 30f * mapScale// 文字サイズ
                        textAlign = Paint.Align.CENTER
                    }
                    canvas.nativeCanvas.drawText(
                        name, // 都道府県名
                        (x + width / 2) * mapScale + mapOffset.x, // 矩形の中心X座標
                        (y + height / 2) * mapScale + 20f + mapOffset.y, // 矩形の中心Y座標（少し下に調整）
                        textPaint
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .padding(30.dp)
                .height(100.dp)
                .width(100.dp)
                .background(Color(102,212,230))
        ){
            if ( !mapUiState.prefecture.isNullOrEmpty() ){
                Text(
                    modifier = modifier.align(Alignment.Center),
                    text = "${mapUiState.prefecture}"
                )
            }
        }
    }
}