package me.brunofelix.cleandictionary.core.util

import android.media.AudioAttributes
import android.media.MediaPlayer

fun playAudio(url: String) {
    MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        setDataSource(url)
        prepare()
        start()
    }
}