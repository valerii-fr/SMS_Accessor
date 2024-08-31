package dev.nordix.smsaccessor.presentation.contacts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nordix.accessors.domain.ContactsAccessor
import dev.nordix.accessors.model.CustomerContact
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    contactsAccessor: ContactsAccessor,
) : ViewModel() {

    val contacts: StateFlow<List<CustomerContact>> = contactsAccessor.contacts

}
