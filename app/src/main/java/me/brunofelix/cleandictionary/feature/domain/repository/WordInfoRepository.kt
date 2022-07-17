package me.brunofelix.cleandictionary.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import me.brunofelix.cleandictionary.core.util.Resource
import me.brunofelix.cleandictionary.feature.domain.model.WordInfo

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}