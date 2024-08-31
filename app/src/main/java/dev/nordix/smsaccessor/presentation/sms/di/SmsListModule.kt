package dev.nordix.smsaccessor.presentation.sms.di

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nordix.smsaccessor.presentation.sms.SmsListViewModel

@Composable
internal fun smsListViewModel(phoneNumber: String): SmsListViewModel {
    return hiltViewModel<SmsListViewModel, SmsListViewModel.Factory>(
        creationCallback = { factory -> factory.create(phoneNumber) }
    )
}