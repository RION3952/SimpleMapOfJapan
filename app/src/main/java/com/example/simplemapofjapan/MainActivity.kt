package com.example.simplemapofjapan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.simplemapofjapan.ui.MapOfJapan
import com.example.simplemapofjapan.ui.theme.SimpleMapOfJapanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleMapOfJapanTheme {
                MapOfJapan()
            }
        }
    }
}
