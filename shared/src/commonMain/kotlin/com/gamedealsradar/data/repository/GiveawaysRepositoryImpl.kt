package com.gamedealsradar.data.repository

import com.gamedealsradar.data.mapper.toGiveaway
import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.data.model.GiveawayDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class GiveawaysRepositoryImpl(
    private val supabaseClient: SupabaseClient,
) : GiveawayRepository {

    override suspend fun getGiveaways(): List<Giveaway> {
        return supabaseClient
            .from("giveaways")
            .select()
            .decodeList<GiveawayDto>()
            .map { it.toGiveaway() }
    }
}
