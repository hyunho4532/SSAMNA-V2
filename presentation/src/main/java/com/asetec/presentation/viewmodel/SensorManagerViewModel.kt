package com.asetec.presentation.viewmodel

import android.content.Context
import android.hardware.SensorEventListener
import androidx.lifecycle.ViewModel
import com.asetec.domain.model.location.Location
import com.asetec.domain.model.state.Activate
import com.asetec.domain.usecase.sensor.SensorManagerCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SensorManagerViewModel @Inject constructor(
    private val sensorManagerCase: SensorManagerCase,
    @ApplicationContext private val appContext: Context
): ViewModel() {

    private val sharedPreferences = appContext.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)

    private val _activates = MutableStateFlow(Activate())

    val activates: StateFlow<Activate> = _activates

    fun startService(context: Context) {
        sensorManagerCase.startService(context)

        _activates.update {
            it.copy(
                activateButtonName = "측정 중!"
            )
        }
    }

    fun stopService(context: Context, isRunning: Boolean) {
        sensorManagerCase.stopService(context = context)

        _activates.update {
            it.copy(
                showRunningStatus = isRunning,
                activateButtonName = "측정하기!"
            )
        }
    }

    fun sensorEventListener(): SensorEventListener {
        return sensorManagerCase.sensorListener { stepCount ->
            _activates.update {
                it.copy(
                    pedometerCount = stepCount
                )
            }
        }
    }

    fun getSavedSensorState(): Int {
        return sharedPreferences.getInt("pedometerCount", _activates.value.pedometerCount)
    }

    fun setSavedSensorState() {
        sharedPreferences.edit().putInt("pedometerCount", _activates.value.pedometerCount).apply()
    }
}