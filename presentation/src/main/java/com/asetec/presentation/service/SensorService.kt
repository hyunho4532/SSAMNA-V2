package com.asetec.presentation.service

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.IBinder
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import javax.inject.Inject


class SensorService @Inject constructor(
    private val activityLocationViewModel: ActivityLocationViewModel
): Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun sensorListener(): SensorEventListener {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    activityLocationViewModel.activates.value.pedometerCount = it.values[0].toInt()
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                TODO("Not yet implemented")
            }
        }

        return listener
    }
}