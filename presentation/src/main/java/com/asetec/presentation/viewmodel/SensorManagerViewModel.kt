package com.asetec.presentation.viewmodel

import android.content.Context
import android.hardware.SensorEventListener
import android.util.Log
import androidx.lifecycle.ViewModel
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

    private var sharedPreferences = appContext.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)

    private val _activates = MutableStateFlow(Activate())

    val activates: StateFlow<Activate> = _activates

    fun startService(context: Context, isRunning: Boolean) {
        sensorManagerCase.startService(context)

        _activates.update {
            it.copy(
                activateButtonName = "측정 중!",
                isRunning = isRunning
            )
        }

        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()
    }

    fun stopService(context: Context, runningStatus: Boolean, isRunning: Boolean) {
        sensorManagerCase.stopService(context = context)

        _activates.update {
            it.copy(
                showRunningStatus = runningStatus,
                isRunning = isRunning,
                activateButtonName = "측정하기!"
            )
        }

        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()

        /**
         * 측정이 완료되었으므로, 걸음을 0으로 초기화한다.
         */
        initSavedSensorState()
    }

    fun sensorEventListener(): SensorEventListener {
        return sensorManagerCase.sensorListener(getSavedSensorState()) { stepCount ->
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

    fun getSavedIsRunningState(): Boolean {
        return sharedPreferences.getBoolean("showRunning", _activates.value.isRunning)
    }

    fun setSavedSensorState() {
        sharedPreferences.edit().putInt("pedometerCount", _activates.value.pedometerCount).apply()
    }

    fun initSavedSensorState() {
        sharedPreferences.edit().putInt("pedometerCount", 0).apply()
    }
}