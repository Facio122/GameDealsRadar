package com.gamedealsradar.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.gamedealsradar.presentation.dealsmain.MainRoute
import com.gamedealsradar.presentation.utils.AppColors
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val AppSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(AppRoute.Main::class)
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColors.Surface
        ) {
            val backStack = rememberNavBackStack(
                AppSavedStateConfiguration,
                AppRoute.Main
            )

            NavDisplay(
                modifier = Modifier.systemBarsPadding(),
                backStack = backStack,
                entryProvider = entryProvider {
                    entry<AppRoute.Main> {
                        MainRoute()
                    }
                }
            )
        }
    }
}
