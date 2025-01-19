package com.asetec.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivateDTO(
    @SerialName("google_id")
    val googleId: String = "",

    @SerialName("status")
    val status: Int = 0,

    @SerialName("title")
    val title: String = "",

    @SerialName("goal_count")
    val goalCount: Int = 0
)