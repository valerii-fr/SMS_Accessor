package dev.nordix.smsaccessor.presentation.sms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nordix.accessors.domain.SmsAccessor
import dev.nordix.accessors.model.SmsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = SmsListViewModel.Factory::class)
class SmsListViewModel @AssistedInject constructor(
    private val smsAccessor: SmsAccessor,
    @Assisted private val phoneNumber: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String) : SmsListViewModel
    }

    val smsList: StateFlow<List<SmsItem>>
        field = MutableStateFlow(emptyList())

    private val selectedNumber = MutableStateFlow<String>(phoneNumber)

    init {
        selectedNumber.mapLatest { number ->
            smsList.update {
                smsAccessor.getSmsForNumber(number)
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}
