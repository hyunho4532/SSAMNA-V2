package com.asetec.data.repository.json

import android.content.Context
import android.content.res.AssetManager
import com.asetec.domain.model.state.Activate
import com.asetec.domain.repository.json.JsonParsingRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.utils.io.core.use
import javax.inject.Inject

class JsonParsingRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
): JsonParsingRepository {

    private val gson = Gson()

    private val activateListType = object : TypeToken<List<Activate>>() {}.type

    override fun jsonParse(jsonFile: String): List<Activate> {

        val assetManager: AssetManager = context.assets

        val json: String = assetManager.open(jsonFile).bufferedReader().use {
            it.readText()
        }

        return gson.fromJson(json, activateListType)
    }
}