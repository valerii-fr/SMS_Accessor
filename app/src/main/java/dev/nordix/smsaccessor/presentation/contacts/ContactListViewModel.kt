package dev.nordix.smsaccessor.presentation.contacts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nordix.smsaccessor.component.ContactsAccessor
import dev.nordix.smsaccessor.domain.CustomerContact
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    contactsAccessor: ContactsAccessor,
) : ViewModel() {

    val contacts: StateFlow<List<CustomerContact>> = contactsAccessor.contacts

}
