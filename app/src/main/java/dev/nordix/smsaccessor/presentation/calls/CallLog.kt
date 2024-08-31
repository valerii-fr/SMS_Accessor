package dev.nordix.smsaccessor.presentation.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallMissedOutgoing
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nordix.smsaccessor.common_ui.helpers.DateTimeHelper.formatter
import dev.nordix.accessors.model.CallItem
import dev.nordix.smsaccessor.presentation.calls.di.callLogViewModel
import dev.nordix.smsaccessor.presentation.sms.SmsListDefaults.MaxWidth
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.time.DurationUnit

@Composable
fun CallLog(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    viewModel: CallLogViewModel = callLogViewModel(phoneNumber)
) {
    val callLog by viewModel.callLog.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(
            items = callLog,
            key = { it.hashCode() },
            itemContent = { item ->
                CallLogItem(item)
                Spacer(Modifier.height(4.dp))
            }
        )
    }
}

@Composable
private fun CallLogItem(
    callItem: CallItem
) {
    val alignment by remember(callItem) {
        mutableStateOf(
            if(callItem.type == CallItem.CallType.OUTGOING) {
                Alignment.CenterEnd
            } else {
                Alignment.CenterStart
            }
        )
    }
    val alignmentHorizontal by remember(callItem) {
        mutableStateOf(
            if(callItem.type == CallItem.CallType.OUTGOING) {
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
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ),
            elevation = CardDefaults.elevatedCardElevation(1.dp),
        ) {
            Row(
                modifier = Modifier.wrapContentWidth().padding(8.dp).widthIn(max = MaxWidth),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = when(callItem.type) {
                        CallItem.CallType.INCOMING -> Icons.AutoMirrored.Default.CallReceived
                        CallItem.CallType.OUTGOING -> Icons.AutoMirrored.Default.CallMade
                        CallItem.CallType.MISSED -> Icons.AutoMirrored.Default.CallMissed
                        CallItem.CallType.REJECTED -> Icons.AutoMirrored.Default.CallMissedOutgoing
                    },
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = alignmentHorizontal
                ) {
                    Text(
                        text = formatter.format(LocalDateTime.ofInstant(callItem.date, ZoneOffset.systemDefault())),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = callItem.duration.toString(unit = DurationUnit.MINUTES, decimals = 2),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

}
