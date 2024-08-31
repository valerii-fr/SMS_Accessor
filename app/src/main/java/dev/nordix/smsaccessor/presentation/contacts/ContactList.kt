package dev.nordix.smsaccessor.presentation.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import dev.nordix.smsaccessor.domain.CustomerContact

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    viewModel: ContactListViewModel = hiltViewModel<ContactListViewModel>(),
    onContactSelect: (phoneNumber: String) -> Unit,
) {
    val contacts by viewModel.contacts.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = contacts,
            key = { it.phoneNumber },
            itemContent = { item ->
                CustomerContactItem(
                    contact = item,
                    onSelect = onContactSelect
                )
            }
        )
    }

}

@Composable
private fun CustomerContactItem(
    contact: CustomerContact,
    onSelect: (String) -> Unit,
) {
    Card(
        colors = CardDefaults.elevatedCardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        ),
        elevation = CardDefaults.elevatedCardElevation(1.dp),
        onClick = { onSelect(contact.phoneNumber) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
