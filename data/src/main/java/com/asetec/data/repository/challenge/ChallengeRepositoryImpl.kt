package com.asetec.data.repository.challenge

import com.asetec.domain.model.state.ChallengeDTO
import com.asetec.domain.repository.challenge.ChallengeRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): ChallengeRepository {
    override suspend fun insert(challengeDTO: ChallengeDTO) {
        postgrest.from("Challenge").insert(challengeDTO)
    }
}