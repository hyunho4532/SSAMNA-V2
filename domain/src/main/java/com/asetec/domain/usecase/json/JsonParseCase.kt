package com.asetec.domain.usecase.json

import com.asetec.domain.model.state.Activate
import com.asetec.domain.repository.json.JsonParsingRepository
import javax.inject.Inject

class JsonParseCase @Inject constructor(
    private val jsonParsingRepository: JsonParsingRepository
) {
    fun invoke(jsonFile: String): List<Activate> {
        return jsonParsingRepository.jsonParse(jsonFile)
    }
}