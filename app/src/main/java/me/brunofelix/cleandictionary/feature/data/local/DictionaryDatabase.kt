package me.brunofelix.cleandictionary.feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.brunofelix.cleandictionary.feature.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract val dao: WordInfoDao
}