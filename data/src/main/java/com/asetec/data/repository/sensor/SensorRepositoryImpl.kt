package com.asetec.data.repository.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.asetec.domain.repository.sensor.SensorRepository
import javax.inject.Inject

class SensorRepositoryImpl @Inject constructor(

): SensorRepository, SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var stepCount = 0

    override fun updateNotification(stepCount: Int) {

    }

    override fun sensorListener(
        setStepCount: (Int) -> Unit
    ): SensorEventListener {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    stepCount = it.values[0].toInt()
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        }

        setStepCount(stepCount)

        return listener
    }

    override fun onSensorChanged(event: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}