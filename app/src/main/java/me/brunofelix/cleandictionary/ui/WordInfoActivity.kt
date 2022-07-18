package me.brunofelix.cleandictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.brunofelix.cleandictionary.R
import me.brunofelix.cleandictionary.databinding.ActivityWordInfoBinding
import me.brunofelix.cleandictionary.extension.hideKeyboard
import me.brunofelix.cleandictionary.extension.showToast
import me.brunofelix.cleandictionary.feature.presentation.UIEvent
import me.brunofelix.cleandictionary.feature.presentation.WordInfoViewModel

@AndroidEntryPoint
class WordInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordInfoBinding
    private val viewModel: WordInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiSetup()
        uiState()
    }

    private fun uiSetup() {
        setTheme(R.style.Theme_CleanDictionary)
        window.setBackgroundDrawableResource(R.drawable.bg_gradient)

        binding = ActivityWordInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_dark_mode -> {
                    // TODO: call bottom sheets dialog
                    true
                }
                else -> false
            }
        }

        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            viewModel.onSearch(binding.textInputWord.text.toString())
        }

        binding.textInputWord.setOnEditorActionListener { textView, i, keyEvent ->
            hideKeyboard()
            viewModel.onSearch(binding.textInputWord.text.toString())
            true
        }
    }

    private fun uiState() {
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when(event) {
                    is UIEvent.ShowSnackbar -> {
                        showToast(event.message)
                    }
                }
            }
        }
    }
}
