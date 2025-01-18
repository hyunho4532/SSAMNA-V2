package com.asetec.domain.usecase.sensor

import android.hardware.SensorEventListener
import com.asetec.domain.repository.sensor.SensorRepository
import javax.inject.Inject

class SensorCase @Inject constructor(
    private val sensorRepository: SensorRepository
) {

    private var stepCount: Int = 0

    fun invoke() {
    }

    fun sensorListener(setStepCount: (Int) -> Unit): SensorEventListener {
        return sensorRepository.sensorListener {
            setStepCount(it)
            stepCount = it
        }
    }
}