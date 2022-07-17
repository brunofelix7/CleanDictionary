package me.brunofelix.cleandictionary.feature.presentation

import me.brunofelix.cleandictionary.feature.domain.model.WordInfo

data class WordInfoState(
    val isLoading: Boolean = false,
    val wordInfoItems: List<WordInfo> = emptyList()
)