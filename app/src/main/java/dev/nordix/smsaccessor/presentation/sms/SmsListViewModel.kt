package dev.nordix.smsaccessor.presentation.sms

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel(assistedFactory = SmsListViewModel.Factory::class)
class SmsListViewModel @AssistedInject constructor(
    @ApplicationContext context: Context,
    @Assisted private val phoneNumber: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String) : SmsListViewModel
    }

    private val selectedNumber = MutableStateFlow<String?>(null)

    fun setNumber(number: String) {
        selectedNumber.value = number
    }

}
