package me.brunofelix.cleandictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.brunofelix.cleandictionary.BuildConfig
import me.brunofelix.cleandictionary.core.util.GsonParser
import me.brunofelix.cleandictionary.feature.data.local.Converters
import me.brunofelix.cleandictionary.feature.data.local.DictionaryDatabase
import me.brunofelix.cleandictionary.feature.data.remote.DictionaryApi
import me.brunofelix.cleandictionary.feature.data.repository.WordInfoRepositoryImpl
import me.brunofelix.cleandictionary.feature.domain.repository.WordInfoRepository
import me.brunofelix.cleandictionary.feature.domain.usecase.GetWordInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoApp {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo =
        GetWordInfo(repository)
    
    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: DictionaryDatabase,
        api: DictionaryApi): WordInfoRepository = WordInfoRepositoryImpl(api, db.dao)

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): DictionaryDatabase =
        Room.databaseBuilder(app, DictionaryDatabase::class.java, "word_db")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
}
