package dev.nordix.smsaccessor.presentation.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import dev.nordix.smsaccessor.common_ui.ClientIcon
import dev.nordix.smsaccessor.domain.CustomerContact

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    viewModel: ContactListViewModel = hiltViewModel<ContactListViewModel>(),
    onContactSelect: (contact: CustomerContact) -> Unit,
) {
    val contacts by viewModel.contacts.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        state = listState
    ) {
        items(
            items = contacts,
            key = { it.phoneNumber },
            itemContent = { item ->
                CustomerContactItem(
                    contact = item,
                    onSelect = remember {{ onContactSelect(item) }}
                )
                Spacer(Modifier.height(4.dp))
            }
        )
    }

}

@Composable
private fun CustomerContactItem(
    contact: CustomerContact,
    onSelect: () -> Unit,
) {
    Card(
        colors = CardDefaults.elevatedCardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        ),
        elevation = CardDefaults.elevatedCardElevation(1.dp),
        onClick = onSelect,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ClientIcon(
                name = contact.name,
                modifier = Modifier
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = contact.phoneNumber,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = contact.id,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
