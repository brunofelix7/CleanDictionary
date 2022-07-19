package me.brunofelix.cleandictionary.extension

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import me.brunofelix.cleandictionary.R

fun Context.changeTheme() {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val key = getString(R.string.theme_key)

    when (prefs.getBoolean(key, false)) {
        true -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        false -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}

fun Activity.updateTheme(setDarkMode: Boolean) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val key = getString(R.string.theme_key)

    prefs.edit().apply {
        putBoolean(key, setDarkMode)
        apply()
    }
    changeTheme()
    recreate()
}

fun Context.isInDarkMode(): Boolean {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val key = getString(R.string.theme_key)
    return prefs.getBoolean(key, false)
}
