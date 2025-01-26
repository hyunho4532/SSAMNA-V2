package com.asetec.domain.repository.challenge

import com.asetec.domain.model.state.ChallengeDTO


interface ChallengeRepository {
    suspend fun insert(challengeDTO: ChallengeDTO)
}