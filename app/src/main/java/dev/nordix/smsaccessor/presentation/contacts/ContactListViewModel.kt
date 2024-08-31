package dev.nordix.smsaccessor.presentation.contacts

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.nordix.smsaccessor.domain.CustomerContact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {

    val contentResolver = context.contentResolver

    val contacts: StateFlow<List<CustomerContact>>
        field = MutableStateFlow(emptyList())

    init {
        getContacts()
    }

    fun getContacts() {

        val contactsList = mutableListOf<CustomerContact>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                val id = it.getString(idIndex)
                contactsList.add(
                    CustomerContact(
                        name = name,
                        phoneNumber = number,
                        id = id,
                    )
                )
            }
            it.close()
        }

        contacts.update { contactsList }
    }
}
