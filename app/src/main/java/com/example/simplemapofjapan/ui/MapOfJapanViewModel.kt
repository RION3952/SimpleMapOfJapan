package com.example.simplemapofjapan.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapOfJapanViewModel(
): ViewModel() {
    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState: StateFlow<MapUiState> =  _mapUiState.asStateFlow()

    val hokkaidoColor: Color = Color(120,140,230)
    val tohokuColor: Color = Color(80,160,230)
    val kantoColor: Color = Color(100,210,215)
    val chubuColor: Color = Color(100,205,130)
    val kinkiColor: Color = Color(220,80,200)
    val chugokuColor: Color = Color(250,180,80)
    val ShikokuColor: Color = Color(240,220,10)
    val kyushuColor: Color = Color(255,100,10)

    val prefectures = listOf(
        // (x座標, y座標, 幅, 高さ, 都道府県名)
        // 北海道
        Prefecture(13f, 1f, 4f, 3f, "北海道", hokkaidoColor),
        // 東北
        Prefecture(13f, 5f, 2f, 1f, "青森", tohokuColor),
        Prefecture(13f, 6f, 1f, 1f, "秋田", tohokuColor),
        Prefecture(14f, 6f, 1f, 1f, "岩手", tohokuColor),
        Prefecture(13f, 7f, 1f, 1f, "山形", tohokuColor),
        Prefecture(14f, 7f, 1f, 1f, "宮城", tohokuColor),
        Prefecture(13f, 8f, 2f, 1f, "福島", tohokuColor),
        // 関東
        Prefecture(13f, 9f, 1f, 1f, "群馬", kantoColor),
        Prefecture(14f, 9f, 1f, 1f, "栃木", kantoColor),
        Prefecture(13f, 10f, 1f, 1f, "埼玉", kantoColor),
        Prefecture(14f, 10f, 1f, 1f, "茨城", kantoColor),
        Prefecture(13f, 11f, 1f, 1f, "東京", kantoColor),
        Prefecture(14f, 11f, 1f, 2f, "千葉", kantoColor),
        Prefecture(13f, 12f, 1f, 1f, "神奈川", kantoColor),
        // 中部
        Prefecture(10f, 8f, 1f, 1f, "石川", chubuColor),
        Prefecture(11f, 8f, 1f, 1f, "富山", chubuColor),
        Prefecture(12f, 8f, 1f, 1f, "新潟", chubuColor),
        Prefecture(10f, 9f, 1f, 1f, "福井", chubuColor),
        Prefecture(11f, 9f, 1f, 2f, "岐阜", chubuColor),
        Prefecture(12f, 9f, 1f, 2f, "長野", chubuColor),
        Prefecture(11f, 11f, 1f, 1f, "愛知", chubuColor),
        Prefecture(12f, 11f, 1f, 1f, "山梨", chubuColor),
        Prefecture(12f, 12f, 1f, 1f, "静岡", chubuColor),
        // 近畿
        Prefecture(8f, 10f, 1f, 1f, "兵庫", kinkiColor),
        Prefecture(9f, 10f, 1f, 1f, "京都", kinkiColor),
        Prefecture(10f, 10f, 1f, 1f, "滋賀", kinkiColor),
        Prefecture(8f, 11f, 1f, 1f, "大阪", kinkiColor),
        Prefecture(9f, 11f, 1f, 1f, "奈良", kinkiColor),
        Prefecture(10f, 11f, 1f, 1f, "三重", kinkiColor),
        Prefecture(8f, 12f, 2f, 1f, "和歌山", kinkiColor),
        // 中国
        Prefecture(5f, 10f, 1f, 2f, "山口", chugokuColor),
        Prefecture(6f, 10f, 1f, 1f, "島根", chugokuColor),
        Prefecture(7f, 10f, 1f, 1f, "鳥取", chugokuColor),
        Prefecture(6f, 11f, 1f, 1f, "広島", chugokuColor),
        Prefecture(7f, 11f, 1f, 1f, "岡山", chugokuColor),
        // 四国
        Prefecture(5f, 13f, 1f, 1f, "愛媛", ShikokuColor),
        Prefecture(6f, 13f, 1f, 1f, "香川", ShikokuColor),
        Prefecture(5f, 14f, 1f, 1f, "高知", ShikokuColor),
        Prefecture(6f, 14f, 1f, 1f, "徳島", ShikokuColor),
        // 九州
        Prefecture(1f, 11f, 1f, 1f, "長崎", kyushuColor),
        Prefecture(2f, 11f, 1f, 1f, "佐賀", kyushuColor),
        Prefecture(3f, 11f, 1f, 1f, "大分", kyushuColor),
        Prefecture(2f, 12f, 1f, 2f, "熊本", kyushuColor),
        Prefecture(3f, 12f, 1f, 1f, "大分", kyushuColor),
        Prefecture(3f, 13f, 1f, 1f, "宮崎", kyushuColor),
        Prefecture(2f, 14f, 2f, 1f, "鹿児島", kyushuColor),
        Prefecture(1f, 16f, 1f, 1f, "沖縄", kyushuColor),
        )

    fun prefectureClicked(prefecture:String){
        _mapUiState.update { uiState ->
            uiState.copy(
                prefecture = prefecture
            )
        }
    }
}

data class Prefecture(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val name: String,
    val color: Color,
)

data class MapUiState(
    val prefecture: String? = null
)