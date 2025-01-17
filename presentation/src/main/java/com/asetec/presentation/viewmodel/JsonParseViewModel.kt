package com.asetec.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.asetec.domain.model.state.Activate
import com.asetec.domain.usecase.json.JsonParseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class JsonParseViewModel @Inject constructor(
    private val jsonParseCase: JsonParseCase,
) : ViewModel() {

    private val _activateJsonData = mutableListOf<Activate>()
    val activateJsonData = _activateJsonData

    fun activateJsonParse(fileName: String) {
        val activateData = jsonParseCase.invoke(fileName)

        for (activate in activateData) {
            _activateJsonData.add(activate)
        }
    }
}