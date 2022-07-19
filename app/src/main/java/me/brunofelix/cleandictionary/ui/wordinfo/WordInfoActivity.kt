package me.brunofelix.cleandictionary.ui.wordinfo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brunofelix.cleandictionary.R
import me.brunofelix.cleandictionary.databinding.ActivityWordInfoBinding
import me.brunofelix.cleandictionary.extension.hideKeyboard
import me.brunofelix.cleandictionary.extension.showToast
import me.brunofelix.cleandictionary.feature.domain.model.Meaning
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
            when (it.itemId) {
                R.id.action_dark_mode -> {
                    // TODO: call bottom sheets dialog
                    true
                }
                else -> false
            }
        }

        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            clearFields()
            viewModel.onSearch(binding.textInputWord.text.toString())
        }

        binding.textInputWord.setOnEditorActionListener { textView, i, keyEvent ->
            hideKeyboard()
            clearFields()
            viewModel.onSearch(binding.textInputWord.text.toString())
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun uiState() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        showToast(event.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.isVisible = state.isLoading

                var meaningsList: List<Meaning> = mutableListOf()

                if (state.wordInfoItems.isEmpty()) {
                    binding.contentLayout.isVisible = false
                } else {
                    binding.contentLayout.isVisible = true

                    for (item in state.wordInfoItems) {
                        binding.textWord.text = item.word
                        binding.textPhonetic.text = item.phonetic

                        meaningsList = item.meanings

                        for (item in meaningsList) {
                            when (item.partOfSpeech) {
                                "noun" -> {
                                    binding.nounLayout.isVisible = true
                                    binding.textNoun.text = item.partOfSpeech
                                    for (definition in item.definitions) {
                                        binding.textNounDefinitions.text =
                                            "${binding.textNounDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                                "verb" -> {
                                    binding.verbLayout.isVisible = true
                                    binding.textVerb.text = item.partOfSpeech
                                    for (definition in item.definitions) {
                                        binding.textVerbDefinitions.text =
                                            "${binding.textVerbDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                                "adjective" -> {
                                    binding.adjectiveLayout.isVisible = true
                                    binding.textAdjective.text = item.partOfSpeech
                                    for (definition in item.definitions) {
                                        binding.textAdjectiveDefinitions.text =
                                            "${binding.textAdjectiveDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun clearFields() {
        binding.textNoun.text = ""
        binding.textVerb.text = ""
        binding.textAdjective.text = ""
        binding.textNounDefinitions.text = ""
        binding.textVerbDefinitions.text = ""
        binding.textAdjectiveDefinitions.text = ""
    }
}
