package com.gamedealsradar.di

import com.gamedealsradar.data.room.AppDatabase
import com.gamedealsradar.domain.repository.GiveawayRepository
import com.gamedealsradar.data.repository.GiveawaysRepositoryImpl
import com.gamedealsradar.domain.usecase.GetFiltersUseCase
import com.gamedealsradar.presentation.dealsmain.MainViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://cxyfyedtlkgyiisxpyto.supabase.co",
            supabaseKey = "sb_publishable_n8GqRx3ileTibN26jKS5bw_7CrRc1k4"
        ) {
            install(Postgrest)
        }
    }

    single { get<AppDatabase>().giveawayDao() }

    single<GiveawayRepository> {
        GiveawaysRepositoryImpl(get(), get())
    }
}

val domainModule = module {

    single<GetFiltersUseCase> {
        GetFiltersUseCase()
    }
}

val presentationModule = module {
    factory { MainViewModel(get(), get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(dataModule, domainModule, presentationModule, platformModule)
    }
