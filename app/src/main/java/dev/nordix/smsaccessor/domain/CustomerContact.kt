package dev.nordix.smsaccessor.domain

import java.util.UUID

data class CustomerContact(
    val id: String,
    val name: String,
    val phoneNumber: String,
)
