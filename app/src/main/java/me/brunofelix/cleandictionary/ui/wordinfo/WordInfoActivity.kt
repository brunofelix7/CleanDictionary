package me.brunofelix.cleandictionary.ui.wordinfo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brunofelix.cleandictionary.R
import me.brunofelix.cleandictionary.core.util.playAudio
import me.brunofelix.cleandictionary.databinding.ActivityWordInfoBinding
import me.brunofelix.cleandictionary.extension.*
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
                    if (isInDarkMode()) {
                        updateTheme(setDarkMode = false)
                    } else {
                        updateTheme(setDarkMode = true)
                    }
                    true
                }
                else -> false
            }
        }

        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            clearFields()
            viewModel.onSearch(binding.textInputWord.text.toString().trim().lowercase())
        }

        binding.textInputWord.setOnEditorActionListener { textView, i, keyEvent ->
            hideKeyboard()
            clearFields()
            viewModel.onSearch(binding.textInputWord.text.toString().trim().lowercase())
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

                var meaningsList: List<Meaning> = emptyList()
                var audioUrl = ""

                if (state.wordInfoItems.isEmpty()) {
                    binding.contentLayout.isVisible = false
                } else {
                    binding.contentLayout.isVisible = true

                    for (item in state.wordInfoItems) {
                        binding.textWord.text = item.word
                        binding.textPhonetic.text = item.phonetic

                        meaningsList = item.meanings

                        for (phonetic in item.phonetics) {
                            if (phonetic.audio.isNotEmpty()) {
                                audioUrl = phonetic.audio
                            }
                        }

                        for (meaning in meaningsList) {
                            when (meaning.partOfSpeech) {
                                "noun" -> {
                                    binding.nounLayout.isVisible = true
                                    binding.textNoun.text = meaning.partOfSpeech
                                    for (definition in meaning.definitions) {
                                        binding.textNounDefinitions.text =
                                            "${binding.textNounDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                                "verb" -> {
                                    binding.verbLayout.isVisible = true
                                    binding.textVerb.text = meaning.partOfSpeech
                                    for (definition in meaning.definitions) {
                                        binding.textVerbDefinitions.text =
                                            "${binding.textVerbDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                                "adjective" -> {
                                    binding.adjectiveLayout.isVisible = true
                                    binding.textAdjective.text = meaning.partOfSpeech
                                    for (definition in meaning.definitions) {
                                        binding.textAdjectiveDefinitions.text =
                                            "${binding.textAdjectiveDefinitions.text}" + "- " + definition.definition + "\n"
                                    }
                                }
                            }
                        }
                    }

                    binding.imgAudio.setOnClickListener {
                        playAudio(url = audioUrl)
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
