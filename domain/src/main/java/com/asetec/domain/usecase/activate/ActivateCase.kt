package com.asetec.domain.usecase.activate

import com.asetec.domain.model.state.Activate
import com.asetec.domain.repository.activate.ActivateRepository
import javax.inject.Inject

class ActivateCase @Inject constructor(
    private val activateRepository: ActivateRepository
) {
    suspend fun saveActivity(activate: Activate) {
        activateRepository.insert(activate)
    }
}