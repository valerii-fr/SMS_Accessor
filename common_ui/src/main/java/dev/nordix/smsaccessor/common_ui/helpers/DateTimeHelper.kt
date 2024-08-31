package dev.nordix.smsaccessor.common_ui.helpers

import java.time.format.DateTimeFormatter

object DateTimeHelper {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' hh:mm")
}