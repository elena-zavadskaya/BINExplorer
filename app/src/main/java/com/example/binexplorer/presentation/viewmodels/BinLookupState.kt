package com.example.binexplorer.presentation.states

import com.example.binexplorer.domain.model.BinInfo

sealed class BinLookupState {
    object Idle : BinLookupState()
    object Loading : BinLookupState()
    data class Success(val binInfo: BinInfo) : BinLookupState()
    data class Error(val message: String) : BinLookupState()
}