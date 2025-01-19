package com.asetec.presentation.component.dialog

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.R
import com.asetec.presentation.component.row.BoxRow
import com.asetec.presentation.component.util.nestedScrollConnection
import com.asetec.presentation.viewmodel.JsonParseViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ShowCompleteDialog(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel,
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {

    var exerciseName by remember {
        mutableStateOf("")
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.runningJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("running.json", "running")
        }
    }

    Dialog(
        onDismissRequest = {
            sensorManagerViewModel.stopService(
                context = context,
                isRunning = false
            )
        }
    ) {
        Card(
            modifier = Modifier
                .width(420.dp)
                .height(560.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "1. 회원님, 이번 운동은 어떠셨나요?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                BoxRow(
                    context = context,
                    data = jsonParseViewModel.runningJsonData
                )

                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "2. 회원님, 제목을 정해주세요!",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(280.dp)
                            .height(56.dp),
                        value = exerciseName,
                        onValueChange = {
                            exerciseName = it
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 14.sp),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.hint_name_exercise),
                                color = Color.Gray
                            )
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "3. 회원님이 운동한 내역",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                GoogleMap(
                    modifier = Modifier
                        .width(300.dp)
                        .height(420.dp)
                        .padding(top = 12.dp),
                    cameraPositionState = cameraPositionState
                ) {

                }
            }
        }
    }
}