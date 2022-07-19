package me.brunofelix.cleandictionary.feature.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import me.brunofelix.cleandictionary.core.util.GsonParser
import me.brunofelix.cleandictionary.feature.domain.model.Meaning
import me.brunofelix.cleandictionary.feature.domain.model.Phonetic

@ProvidedTypeConverter
class Converters constructor(
    private val jsonParser: GsonParser
) {

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromPhoneticsJson(json: String): List<Phonetic> {
        return jsonParser.fromJson<ArrayList<Phonetic>>(
            json,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toPhoneticJson(phonetics: List<Phonetic>): String{
        return jsonParser.toJson(
            phonetics,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: "[]"
    }
}