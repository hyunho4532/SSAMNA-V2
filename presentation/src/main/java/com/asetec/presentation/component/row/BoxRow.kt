package com.asetec.presentation.component.row

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.Running

@SuppressLint("DiscouragedApi")
@Composable
fun BoxRow(
    context: Context,
    data: List<Any>
) {
    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        data.forEach { items ->
            if (items is Activate) {
                Text(
                    text = items.activateName
                )
            }
            else if (items is Running) {
                val imageName = items.assets.replace("R.drawable.", "")
                val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

                Column(
                    modifier = Modifier.padding(
                        top = 12.dp,
                        start = 8.dp
                    )
                ) {
                    Text(
                        text = items.status
                    )

                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = imageResId),
                        contentDescription = "이모티콘 종류 아이콘"
                    )
                }
            }
        }
    }
}