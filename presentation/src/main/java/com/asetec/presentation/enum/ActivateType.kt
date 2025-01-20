package com.asetec.presentation.enum

sealed class CardType {
    sealed class ActivateStatus: CardType() {
        data object 
    }
}