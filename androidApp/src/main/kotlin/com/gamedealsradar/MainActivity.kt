package com.gamedealsradar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.gamedealsradar.data.repository.GiveawayRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val repository: GiveawayRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                val response = repository.getGiveaways()

                Log.d("SUPABASE", "Size: ${response.size}")

                response.forEach {
                    Log.d("SUPABASE", it.title)
                }
            } catch (e: Exception) {
                Log.e("SUPABASE", "ERROR", e)
            }
        }

        setContent {
            App()
        }
    }

    @Preview
    @Composable
    fun AppAndroidPreview() {
        App()
    }
}