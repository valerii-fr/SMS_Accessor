package dev.nordix.smsaccessor.presentation.sms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nordix.smsaccessor.presentation.sms.di.smsListViewModel
import kotlinx.coroutines.launch

@Composable
fun SmsList(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    viewModel: SmsListViewModel = smsListViewModel(phoneNumber),
) {

    LaunchedEffect(phoneNumber) {
        viewModel.setNumber(phoneNumber)
    }

}
