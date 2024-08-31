package dev.nordix.accessors.domain

import android.content.Context
import android.provider.CallLog
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.nordix.accessors.model.CallItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.io.use
import kotlin.time.*
import kotlin.time.toDuration

class CallLogAccessor @Inject constructor(
    @ApplicationContext context: Context
) {

    val contentResolver = context.contentResolver
    val contacts: StateFlow<List<CallItem>>
        field = MutableStateFlow(emptyList())

    fun getCallLogsForNumber(phoneNumber: String) : List<CallItem> {
        val callLogsList = mutableListOf<CallItem>()

        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            "${CallLog.Calls.NUMBER} = ?",
            arrayOf(phoneNumber),
            "${CallLog.Calls.DATE} DESC"
        )

        cursor?.use {
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)

            while (it.moveToNext()) {
                val number = it.getString(numberIndex)
                val date = it.getLong(dateIndex)
                val type = it.getInt(typeIndex)
                val duration = it.getInt(durationIndex)

                callLogsList.add(
                    CallItem(
                        address = number,
                        date = Instant.ofEpochMilli(date),
                        duration = duration.toDuration(DurationUnit.SECONDS),
                        type = when (type) {
                            CallItem.CallType.INCOMING.typeIndex -> CallItem.CallType.INCOMING
                            CallItem.CallType.OUTGOING.typeIndex -> CallItem.CallType.OUTGOING
                            CallItem.CallType.MISSED.typeIndex -> CallItem.CallType.MISSED
                            CallItem.CallType.REJECTED.typeIndex -> CallItem.CallType.REJECTED
                            else -> CallItem.CallType.MISSED
                        }
                    )
                )
            }
        }

        callLogsList.forEach {
            println(it)
        }
        return callLogsList
    }

}