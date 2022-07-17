package me.brunofelix.cleandictionary.feature.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.brunofelix.cleandictionary.core.util.Resource
import me.brunofelix.cleandictionary.feature.data.local.WordInfoDao
import me.brunofelix.cleandictionary.feature.data.remote.DictionaryApi
import me.brunofelix.cleandictionary.feature.domain.model.WordInfo
import me.brunofelix.cleandictionary.feature.domain.repository.WordInfoRepository
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl constructor(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfo(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = wordInfos
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection!",
                data = wordInfos
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}