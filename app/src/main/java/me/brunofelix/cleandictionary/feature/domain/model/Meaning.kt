package me.brunofelix.cleandictionary.feature.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
)