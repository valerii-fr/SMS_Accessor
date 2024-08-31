package dev.nordix.smsaccessor.presentation.calls.di

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nordix.smsaccessor.presentation.calls.CallLogViewModel

@Composable
internal fun callLogViewModel(phoneNumber: String): CallLogViewModel {
    return hiltViewModel<CallLogViewModel, CallLogViewModel.Factory>(
        creationCallback = { factory -> factory.create(phoneNumber) }
    )
}
