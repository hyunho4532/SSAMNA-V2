package com.asetec.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.SensorEventListener
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.location.Location
import com.asetec.domain.model.state.Activate
import com.asetec.domain.usecase.activate.ActivateCase
import com.asetec.domain.usecase.sensor.SensorCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLocationViewModel @Inject constructor(
    private val activateCase: ActivateCase,
    @ApplicationContext appContext: Context
): ViewModel() {

    private var sharedPreferences = appContext.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)
    private val sharedPreferences2 = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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

    fun statusClick(name: String, resId: Int) {
        _activates.update {
            it.copy(
                statusName = name,
                statusIcon = resId
            )
        }
    }

    fun updateRunningTitle(newTitle: String) {
        _activates.value = _activates.value.copy(runningTitle = newTitle)
    }


    /**
     * 활동 저장 버튼 클릭 시 활동 테이블에 데이터 저장
     */
    fun saveActivity() {
        val pedometerCount = sharedPreferences.getInt("pedometerCount", _activates.value.pedometerCount)
        val googleId = sharedPreferences2.getString("id", "")

        val activate = Activate (
            googleId = googleId!!,
            runningTitle = _activates.value.runningTitle,
            statusIcon = _activates.value.statusIcon,
            statusName = _activates.value.statusName,
            pedometerCount = pedometerCount
        )

        viewModelScope.launch {
            activateCase.saveActivity(activate = activate)
        }
    }
}