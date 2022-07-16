package me.brunofelix.cleandictionary.feature.data.local

import androidx.room.*
import me.brunofelix.cleandictionary.feature.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfo(infos: List<WordInfoEntity>)

    @Query("DELETE FROM words WHERE word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("SELECT * FROM words WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word: String): List<WordInfoEntity>
}