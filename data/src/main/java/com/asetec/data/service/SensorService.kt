package com.asetec.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.asetec.data.R


class SensorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var initialStepCount: Int? = null
    private var currentStepCount: Int = 0

    override fun onCreate() {
        super.onCreate()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            Log.d("SensorService", "Step sensor registered")
        } else {
            Log.e("SensorService", "Step sensor not available, stopping service")
            stopSelf()
        }

        startForegroundService()
    }

    private fun startForegroundService() {
        val channelId = "step_counter_channel"
        val channelName = "Step Counter"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .setContentTitle("걸음을 유지하세요!!")
            .setContentText("현재 걸음 수: $currentStepCount")
            .build()

        startForeground(1, notification)
    }

    private fun updateNotification(stepCount: Int) {
        val notification: Notification = NotificationCompat.Builder(this, "step_counter_channel")
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .setContentTitle("걸음을 유지하세요!!")
            .setContentText("현재 걸음 수: $stepCount")
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (initialStepCount == null) {
                initialStepCount = it.values[0].toInt()
            }
            currentStepCount = it.values[0].toInt() - (initialStepCount ?: 0)
            Log.d("SensorService", "Current Step Count: $currentStepCount")

            updateNotification(currentStepCount)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}