package com.asetec.presentation.enum

sealed class ButtonType {
    data object ROUTER: ButtonType()
    sealed class RunningStatus: ButtonType() {
        data object FINISH : RunningStatus()
        data object OPEN: RunningStatus()
    }
}