package com.asetec.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.asetec.presentation.component.icon.Footprint
import com.asetec.presentation.ui.tool.Spacer

/**
 * 구글 지도에서 맨 위에 측정 중인 상태에서 걸음 수를 보여준다.
 */
@Composable
fun TopBox() {
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
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 4.dp)
                )

                Spacer(width = 4.dp, height = 0.dp)

                Icon(
                    imageVector = Footprint,
                    contentDescription = "만보기 아이콘"
                )
            }

            Text(
                text = "0",
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    }
}