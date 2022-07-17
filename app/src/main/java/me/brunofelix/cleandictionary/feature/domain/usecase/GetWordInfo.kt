package me.brunofelix.cleandictionary.feature.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.brunofelix.cleandictionary.core.util.Resource
import me.brunofelix.cleandictionary.feature.domain.model.WordInfo
import me.brunofelix.cleandictionary.feature.domain.repository.WordInfoRepository

class GetWordInfo constructor(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }
        return repository.getWordInfo(word)
    }
}