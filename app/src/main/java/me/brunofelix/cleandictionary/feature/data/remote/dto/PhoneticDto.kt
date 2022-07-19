package me.brunofelix.cleandictionary.feature.data.remote.dto

import me.brunofelix.cleandictionary.feature.domain.model.Phonetic

data class PhoneticDto(
    val audio: String,
    val text: String
) {
    fun toPhonetic(): Phonetic {
        return Phonetic(
            text = text,
            audio = audio
        )
    }
}