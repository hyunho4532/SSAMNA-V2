package com.asetec.domain.manager

import android.content.Context
import android.hardware.SensorEventListener

interface SensorServiceManager {
    fun startSensorService(context: Context)
    fun stopSensorService(context: Context)
    fun sensorListener(setStepCount: (Int) -> Unit): SensorEventListener
    fun updateNotification(stepCount: Int)
}
