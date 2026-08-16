package com.gamedealsradar.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.gamedealsradar.presentation.dealsmain.DealsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val AppSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(AppRoute.DealsMain::class)
        }
    }
}

@Composable
fun App() {
    MaterialTheme {

        val backStack = rememberNavBackStack(AppSavedStateConfiguration, AppRoute.DealsMain)

        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {

                entry<AppRoute.DealsMain> {
                    DealsRoute()
                }
            }
        )
    }
}
