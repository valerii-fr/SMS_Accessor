package dev.nordix.accessors.domain

import android.content.Context
import android.provider.Telephony
import android.util.Log
import dev.nordix.accessors.model.SmsItem
import java.time.Instant
import kotlin.collections.joinToString
import kotlin.io.use

@javax.inject.Singleton
class SmsAccessor @javax.inject.Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext context: Context
) {
    val contentResolver = context.contentResolver

    fun getSmsForNumber(number: String) : List<SmsItem> {

        val smsList = mutableListOf<SmsItem>()

        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            "${Telephony.Sms.ADDRESS} = ?",
            arrayOf(number),
            "${Telephony.Sms.DATE} DESC"
        )

        cursor?.use {
            val bodyIndex = it.getColumnIndex(Telephony.Sms.BODY)
            val dateIndex = it.getColumnIndex(Telephony.Sms.DATE)
            val typeIndex = it.getColumnIndex(Telephony.Sms.TYPE)

            while (it.moveToNext()) {
                val body = it.getString(bodyIndex)
                val date = it.getLong(dateIndex)
                val formattedDate = Instant.ofEpochMilli(date)
                val type = it.getInt(typeIndex)
                smsList.add(
                    SmsItem(
                        address = number,
                        body = body,
                        date = formattedDate,
                        type = when (type) {
                            SmsItem.SmsType.SENT.typeIndex -> SmsItem.SmsType.SENT
                            SmsItem.SmsType.INBOX.typeIndex -> SmsItem.SmsType.INBOX
                            SmsItem.SmsType.OUTBOX.typeIndex -> SmsItem.SmsType.OUTBOX
                            else -> SmsItem.SmsType.SENT
                        }
                    )
                )
            }
        }
        Log.i("${this::class.simpleName}", "got messages \n${smsList.joinToString("\n")}")
        return smsList
    }

}

