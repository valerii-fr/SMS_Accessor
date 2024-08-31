package dev.nordix.smsaccessor.presentation.app.navigation

import com.compose.type_safe_args.annotation.ComposeDestination

sealed interface AppNavigationDirections {

    @ComposeDestination
    interface ContactsList {
        companion object
    }

    @ComposeDestination
    interface SmsList {
        val selectedContactId: String
        companion object
    }
}