package com.asetec.data.repository.activate

import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.repository.activate.ActivateRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivateRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ActivateRepository {
    override suspend fun insert(activateDTO: ActivateDTO) {
        val mappedActivateDTO = ActivateDTO(
            googleId = activateDTO.googleId,
            title = activateDTO.title,
            statusIcon = activateDTO.statusIcon,
            statusTitle = activateDTO.statusTitle,
            goalCount = activateDTO.goalCount,
            kcal_cul = activateDTO.kcal_cul,
            km_cul = activateDTO.km_cul,
            todayFormat = activateDTO.todayFormat
        )

        postgrest.from("Activity").insert(mappedActivateDTO)
    }

    override suspend fun selectActivateById(googleId: String): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activity").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeList<ActivateDTO>()
        }
    }
}