package dev.nordix.smsaccessor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val selectedNumber: StateFlow<String?>
        field: MutableStateFlow<String?> = MutableStateFlow(null)

    fun selectClient(number: String) {
        selectedNumber.update { number }
    }

}
