package com.example.binexplorer.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binexplorer.domain.interactor.BinInteractor
import com.example.binexplorer.domain.util.ResourceResult
import com.example.binexplorer.presentation.states.BinLookupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinLookupViewModel @Inject constructor(
    private val interactor: BinInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<BinLookupState>(BinLookupState.Idle)
    val state: StateFlow<BinLookupState> = _state.asStateFlow()

    fun searchBin(bin: String) {
        if (bin.length < 6) {
            _state.value = BinLookupState.Error("BIN должен содержать не менее 6 цифр")
            return
        }

        viewModelScope.launch {
            _state.value = BinLookupState.Loading
            try {
                when (val result = interactor.getBinInfo(bin)) {
                    is ResourceResult.Success -> {
                        _state.value = BinLookupState.Success(result.data)
                    }
                    is ResourceResult.Error -> {
                        _state.value = BinLookupState.Error(
                            result.exception.message ?: "Произошла ошибка"
                        )
                    }
                    ResourceResult.Loading -> {
                        _state.value = BinLookupState.Loading
                    }
                }
            } catch (e: Exception) {
                _state.value = BinLookupState.Error(e.message ?: "Произошла ошибка")
            }
        }
    }
}