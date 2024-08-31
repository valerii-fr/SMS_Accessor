package dev.nordix.accessors.model

import android.provider.CallLog.Calls.INCOMING_TYPE
import android.provider.CallLog.Calls.MISSED_TYPE
import android.provider.CallLog.Calls.OUTGOING_TYPE
import android.provider.CallLog.Calls.REJECTED_TYPE
import java.time.Instant
import kotlin.time.Duration

data class CallItem(
    val address: String,
    val date: Instant,
    val duration: Duration,
    val type: CallType,
) {
    enum class CallType(val typeIndex: Int) {
        INCOMING(INCOMING_TYPE),
        OUTGOING(OUTGOING_TYPE),
        MISSED(MISSED_TYPE),
        REJECTED(REJECTED_TYPE),
    }
}
