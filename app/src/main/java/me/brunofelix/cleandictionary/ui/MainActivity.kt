package me.brunofelix.cleandictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import me.brunofelix.cleandictionary.R
import me.brunofelix.cleandictionary.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiSetup()
    }

    private fun uiSetup() {
        setTheme(R.style.Theme_CleanDictionary)
        window.setBackgroundDrawableResource(R.drawable.bg_gradient)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_dark_mode -> {
                    // TODO: call bottom sheets dialog
                    Log.d("Bruno", "action dark mode")
                    true
                }
                else -> false
            }
        }

        binding.btnSearch.setOnClickListener {
            Log.d("Bruno", "Search word")
        }
    }
}
