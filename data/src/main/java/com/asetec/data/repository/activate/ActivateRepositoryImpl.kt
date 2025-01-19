package com.asetec.data.repository.activate

import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.repository.activate.ActivateRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ActivateRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ActivateRepository {
    override suspend fun insert(activate: Activate) {
        val activateDTO = ActivateDTO(
            googleId = activate.googleId,
            status = activate.statusIcon,
            title = activate.statusName,
            goalCount = activate.pedometerCount
        )

        postgrest.from("Activity").insert(activateDTO)
    }
}