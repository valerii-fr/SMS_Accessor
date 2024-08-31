package dev.nordix.smsaccessor.presentation.sms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nordix.smsaccessor.common_ui.helpers.DateTimeHelper.formatter
import dev.nordix.smsaccessor.domain.SmsItem
import dev.nordix.smsaccessor.presentation.sms.SmsListDefaults.MaxWidth
import dev.nordix.smsaccessor.presentation.sms.di.smsListViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun SmsList(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    viewModel: SmsListViewModel = smsListViewModel(phoneNumber),
) {

    val smsList by viewModel.smsList.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(
            items = smsList,
            key = { it.hashCode() },
            itemContent = { item ->
                SmsListItem(item)
                Spacer(Modifier.height(4.dp))
            }
        )
    }

}

@Composable
private fun LazyItemScope.SmsListItem(
    sms: SmsItem,
) {
    val alignment by remember(sms) {
        mutableStateOf(
            if(sms.type == SmsItem.SmsType.SENT) {
                Alignment.CenterEnd
            } else {
                Alignment.CenterStart
            }
        )
    }
    val alignmentHorizontal by remember(sms) {
        mutableStateOf(
            if(sms.type == SmsItem.SmsType.SENT) {
                Alignment.End
            } else {
                Alignment.Start
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Card(
            colors = CardDefaults.elevatedCardColors(
                containerColor =  MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ),
            elevation = CardDefaults.elevatedCardElevation(1.dp),
        ) {
            Column(
                modifier = Modifier.wrapContentWidth().padding(8.dp).widthIn(max = MaxWidth),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = alignmentHorizontal
            ) {
                Text(
                    text = sms.body.trim(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatter.format(LocalDateTime.ofInstant(sms.date, ZoneOffset.systemDefault())),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}
