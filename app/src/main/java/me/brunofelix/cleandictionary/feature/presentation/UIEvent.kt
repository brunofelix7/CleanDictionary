package me.brunofelix.cleandictionary.feature.presentation

sealed class UIEvent {
    data class ShowSnackbar(val message: String): UIEvent()
}