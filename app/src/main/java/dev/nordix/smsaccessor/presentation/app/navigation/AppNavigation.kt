package dev.nordix.smsaccessor.presentation.app.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import dev.nordix.smsaccessor.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.nordix.smsaccessor.common_ui.ClientIcon
import dev.nordix.smsaccessor.common_ui.NordixIconButton
import dev.nordix.smsaccessor.domain.CustomerContact
import dev.nordix.smsaccessor.presentation.contacts.ContactList
import dev.nordix.smsaccessor.presentation.sms.SmsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {

    val navHostController = rememberNavController()
    var selectedContact by remember { mutableStateOf<CustomerContact?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { TopBar(navHostController, selectedContact) },
                navigationIcon = {
                    if (navHostController.currentDestination?.route != AppNavigationDirections.ContactsList.route) {
                        NordixIconButton(
                            onClick = { navHostController.popBackStack() },
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = AppNavigationDirections.ContactsList.route,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            composable(
                AppNavigationDirections.ContactsList.route,
                AppNavigationDirections.ContactsList.argumentList
            ) {
                ContactList(onContactSelect = { contact ->
                    selectedContact = contact
                    navHostController.navigate(
                        AppNavigationDirections.SmsList.getDestination(contact.phoneNumber)
                    )
                })
            }

            composable(
                AppNavigationDirections.SmsList.route,
                AppNavigationDirections.SmsList.argumentList
            ) { backStackEntry ->
                val (selectedContactNumber) = AppNavigationDirections.SmsList.parseArguments(backStackEntry)
                SmsList(phoneNumber = selectedContactNumber)
            }
        }
    }

}

@Composable
private fun TopBar(
    navHostController: NavController,
    selectedContact: CustomerContact?
) {
    var titleResId by remember { mutableIntStateOf(R.string.app_name) }
    var currentRoute by remember {
        mutableStateOf<String?>(null)
    }

    navHostController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            currentRoute = destination.route
            titleResId = when (destination.route) {
                AppNavigationDirections.ContactsList.route -> R.string.contacts
                AppNavigationDirections.SmsList.route -> R.string.sms
                else -> R.string.app_name
            }
        }
    })

    when (currentRoute) {
        AppNavigationDirections.SmsList.route -> {
            selectedContact?.let {
                TopBarContactCard(selectedContact)
            } ?: run {
                Text(text = stringResource(titleResId))
            }
        }
        else -> {
            Text(text = stringResource(titleResId))
        }
    }
}

@Composable
private fun TopBarContactCard(
    selectedContact: CustomerContact
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClientIcon(
            name = selectedContact.name,
            size = 40.dp
        )
        Text(text = selectedContact.name)
    }
}
