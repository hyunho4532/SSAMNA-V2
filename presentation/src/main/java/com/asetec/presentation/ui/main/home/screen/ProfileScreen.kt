package com.asetec.presentation.ui.main.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.R
import com.asetec.presentation.component.tool.activateCard
import com.asetec.presentation.enum.CardType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel

@Composable
fun ProfileScreen(
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {

    val activateData  = activityLocationViewModel.activateData.collectAsState()

    LaunchedEffect(key1 = Unit) {
        activityLocationViewModel.selectActivityFindById()
    }


    Column(
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "활동 (${activateData.value.size})",
                fontSize = 22.sp,
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "활동 아이콘"
            )
        }
        activateData.value.forEach { activateDTO ->
            activateCard(
                height = 160.dp,
                shadowElevation = 8,
                activateDTO = activateDTO,
                cardType = CardType.ActivateStatus.Activity
            )
        }
    }
}