package dev.nordix.smsaccessor.presentation.app.navigation

import dev.nordix.smsaccessor.R
import androidx.annotation.StringRes

sealed interface NavTabs {
    @get:StringRes val titleResId: Int

    object CallLog : NavTabs {
        override val titleResId: Int = R.string.call_log
    }

    object SmsLog : NavTabs {
        override val titleResId: Int = R.string.sms_log
    }
}