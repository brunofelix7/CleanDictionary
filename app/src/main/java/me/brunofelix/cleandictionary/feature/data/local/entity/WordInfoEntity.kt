package me.brunofelix.cleandictionary.feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.brunofelix.cleandictionary.feature.domain.model.Meaning
import me.brunofelix.cleandictionary.feature.domain.model.Phonetic
import me.brunofelix.cleandictionary.feature.domain.model.WordInfo

@Entity(tableName = "words")
data class WordInfoEntity(
    @PrimaryKey val id: Int? = null,
    val word: String,
    val phonetic: String,
    val origin: String?,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            origin = origin,
            phonetic = phonetic,
            phonetics = phonetics,
            word = word
        )
    }
}