package com.asetec.presentation.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.asetec.presentation.viewmodel.SensorManagerViewModel

@Composable
fun ShowCompleteDialog(
    sensorManagerViewModel: SensorManagerViewModel
) {
    Dialog(
        onDismissRequest = {
            sensorManagerViewModel.sensorIsRunningFinish(false)
        }
    ) {
        Card(
            modifier = Modifier
                .width(420.dp)
                .height(280.dp),
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
                Text(
                    text = "회원님, 이번 운동은 어떠셨나요?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}