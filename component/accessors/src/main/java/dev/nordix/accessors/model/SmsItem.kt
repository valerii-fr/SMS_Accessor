package dev.nordix.accessors.model

import android.provider.Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX
import android.provider.Telephony.TextBasedSmsColumns.MESSAGE_TYPE_OUTBOX
import android.provider.Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT
import java.time.Instant

data class SmsItem(
    val address: String,
    val body: String,
    val date: Instant,
    val type: SmsType
) {
    enum class SmsType(val typeIndex: Int) {
        SENT(MESSAGE_TYPE_SENT),
        INBOX(MESSAGE_TYPE_INBOX),
        OUTBOX(MESSAGE_TYPE_OUTBOX),
    }
}

