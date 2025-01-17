package com.asetec.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.asetec.domain.model.location.Location
import com.asetec.domain.model.state.Activate
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActivityLocationViewModel @Inject constructor(

): ViewModel() {

    private val _locations = MutableStateFlow(Location(
        latitude = 0.0,
        longitude = 0.0
    ))

    private val _activates = MutableStateFlow(Activate())

    val locations: StateFlow<Location> = _locations
    val activates: StateFlow<Activate> = _activates

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
        isLocationLoaded: (Boolean) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                _locations.value = Location(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                isLocationLoaded(true)
            } else {
                isLocationLoaded(false)
            }
        }
    }

    fun getActivateName(activateResId: Int, activateName: String) {
        _activates.update {
            it.copy(
                activateResId = activateResId,
                activateName = activateName
            )
        }
    }

    /**
     * 클릭 이벤트 리스너
     */
    fun clickListener() {
        _activates.update {
            it.copy(
                activateButtonName = "측정 중!"
            )
        }
    }
}