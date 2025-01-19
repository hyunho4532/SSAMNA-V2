package com.asetec.domain.repository.activate

import com.asetec.domain.model.state.Activate

interface ActivateRepository {
    suspend fun insert(activate: Activate)
}