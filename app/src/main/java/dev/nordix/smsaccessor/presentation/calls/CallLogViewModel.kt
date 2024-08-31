package dev.nordix.smsaccessor.presentation.calls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nordix.smsaccessor.component.CallLogAccessor
import dev.nordix.smsaccessor.domain.CallItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = CallLogViewModel.Factory::class)
class CallLogViewModel @AssistedInject constructor(
    private val callLogAccessor: CallLogAccessor,
    @Assisted private val phoneNumber: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String) : CallLogViewModel
    }

    val callLog: StateFlow<List<CallItem>>
        field = MutableStateFlow(emptyList())

    private val selectedNumber = MutableStateFlow<String>(phoneNumber)

    init {
        selectedNumber.mapLatest { number ->
            callLog.update {
                callLogAccessor.getCallLogsForNumber(number)
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}