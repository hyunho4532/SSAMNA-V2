package com.asetec.presentation.component.box

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.component.icon.Footprint
import com.asetec.presentation.enum.ButtonType
import com.asetec.data.service.SensorService
import com.asetec.presentation.ui.tool.CustomButton
import com.asetec.presentation.ui.tool.Spacer
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel

/**
 * 구글 지도에서 맨 위에 측정 중인 상태에서 걸음 수를 보여준다.
 */
@Composable
fun TopBox(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel()
) {

    val activates = sensorManagerViewModel.activates.collectAsState()

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    LaunchedEffect(key1 = Unit) {
        sensorManagerViewModel.getSavedSensorState()
    }

    DisposableEffect(Unit) {
        val listener = sensorManagerViewModel.sensorEventListener()

        stepDetector?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManagerViewModel.setSavedSensorState()
            stepDetector?.let {
                sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }
    
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "만보기 걸음",
                    fontWeight = FontWeight.Bold
                )

                Spacer(width = 4.dp, height = 0.dp)

                Icon(
                    imageVector = Footprint,
                    contentDescription = "만보기 아이콘"
                )
            }

            Text(
                text = "${activates.value.pedometerCount} 걸음",
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 4.dp)
        ) {
            CustomButton(
                type = ButtonType.RunningStatus.FINISH,
                width = 110.dp,
                height = 32.dp,
                text = "측정 완료!",
                showIcon = false,
                backgroundColor = Color(0xFF5c9afa),
                navController = null,
                context = context,
                shape = "Circle"
            )
        }
    }
}