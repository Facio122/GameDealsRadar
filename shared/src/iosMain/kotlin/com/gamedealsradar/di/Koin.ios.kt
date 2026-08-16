package com.gamedealsradar.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.gamedealsradar.data.room.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
