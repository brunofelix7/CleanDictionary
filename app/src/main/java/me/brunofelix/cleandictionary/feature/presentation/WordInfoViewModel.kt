package me.brunofelix.cleandictionary.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.brunofelix.cleandictionary.core.util.Resource
import me.brunofelix.cleandictionary.feature.domain.usecase.GetWordInfo
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _state = MutableStateFlow(WordInfoState())
    val state: StateFlow<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob = viewModelScope.launch {
            getWordInfo.invoke(query)
                .onEach { result ->
                    when(result) {
                        is Resource.Loading -> {
                            _state.value = WordInfoState(
                                isLoading = true,
                                wordInfoItems = result.data ?: emptyList()
                            )
                        }
                        is Resource.Success -> {
                            _state.value = WordInfoState(
                                isLoading = false,
                                wordInfoItems = result.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            _state.value = WordInfoState(
                                isLoading = false,
                                wordInfoItems = result.data ?: emptyList()
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            ))
                        }
                    }
                }.launchIn(this)
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }
}