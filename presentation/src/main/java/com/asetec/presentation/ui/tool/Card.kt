package com.asetec.presentation.ui.tool

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.user.User
import com.asetec.presentation.viewmodel.ActivityLocationViewModel

@Composable
fun CustomCard(width: Dp, height: Dp, text: String, id: Int) {
    Card (
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(
                elevation = 3.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id = id),
                    contentDescription = "로고",
                    tint = Color.Unspecified
                )
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun ReportCard(userState: User) {

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.9f

    Card (
        modifier = Modifier
            .width(cardWidth)
            .aspectRatio(3f / 4f)
            .shadow(
                elevation = 3.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )
            }

            Text(
                text = "${userState.name} : ${userState.age.toInt()}살",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = userState.email,
                fontSize = 16.sp,
            )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 34.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "최근 운동을 진행한 적이 있으신가요? ${userState.recentExerciseCheck}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "최근 진행하고 있는 운동: ${userState.recentExerciseName}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "하루에 걷기 또는 달리기를 하시나요? ${userState.recentWalkingCheck}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "주: ${userState.recentWalkingOfWeek}회 ${userState.recentWalkingOfTime}분씩 진행!",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "운동 중 목표 기간이 있습니까? ${userState.targetPeriod}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun activateCard(
    context: Context,
    width: Dp,
    height: Dp,
    activate: Activate,
    showBottomSheet: MutableState<Boolean>,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {

    val imageName = activate.assets.replace("R.drawable.", "")

    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Card (
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(top = 8.dp, start = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
                showBottomSheet.value = false
                activityLocationViewModel.getActivateName(
                    activateResId = imageResId,
                    activateName = activate.name
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row {
            Text(
                text = activate.name,
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp)
            )

            Spacer(width = 4.dp, height = 0.dp)

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "활동 종류 아이콘"
            )
        }

        Text(
            text = activate.description,
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Box(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}