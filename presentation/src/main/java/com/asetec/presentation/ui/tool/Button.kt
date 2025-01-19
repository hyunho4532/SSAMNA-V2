package com.asetec.presentation.ui.tool

import android.content.Context
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.asetec.presentation.R
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel

@Composable
fun CustomButton(
    type: ButtonType,
    width: Dp,
    height: Dp,
    text: String,
    showIcon: Boolean,
    backgroundColor: Color,
    navController: NavController?,
    context: Context?,
    shape: String,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {
    Button(
        onClick = {
            if (type == ButtonType.ROUTER) {
                navController?.navigate("login") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            } else {
                when (type) {
                    ButtonType.RunningStatus.FINISH -> {
                        sensorManagerViewModel.stopService(
                            context = context!!,
                            runningStatus = true,
                            isRunning = false
                        )
                    }
                    ButtonType.RunningStatus.INSERT -> {
                        activityLocationViewModel.saveActivity()
                    }
                    else -> {
                        sensorManagerViewModel.startService(context!!, true)
                    }
                }
            }
        },
        modifier = Modifier
            .wrapContentSize()
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = if (shape == "Circle") CircleShape else RectangleShape
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_water_drop_24),
                contentDescription = "운동 여정하기!"
            )

            Spacer(width = 8.dp, height = 0.dp)
        }

        Text(text = text)
    }
}