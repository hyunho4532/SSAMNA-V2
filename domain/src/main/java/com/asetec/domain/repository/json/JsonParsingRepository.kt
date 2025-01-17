package com.asetec.domain.repository.json

import com.asetec.domain.model.state.Activate

interface JsonParsingRepository {
    fun jsonParse(jsonFile: String): List<Activate>
}