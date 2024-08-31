package dev.nordix.smsaccessor.presentation.app

import android.Manifest

object AppDefaults {
    val permissions = listOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_CONTACTS,
    )
}