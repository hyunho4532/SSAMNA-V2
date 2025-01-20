package com.asetec.presentation.ui.main.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.component.icon.SearchPrint
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
        Row {
            Text(
                text = "회원님의 활동 내역",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = SearchPrint,
                contentDescription = "활동 아이콘"
            )
        }
        activateData.value.forEach { data ->
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = data.title
            )
        }
    }
}