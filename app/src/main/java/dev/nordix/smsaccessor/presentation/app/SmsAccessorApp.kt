package dev.nordix.smsaccessor.presentation.app

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nordix.smsaccessor.MainViewModel
import dev.nordix.smsaccessor.presentation.contacts.ContactList
import dev.nordix.smsaccessor.presentation.sms.SmsList
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SmsAccessorApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val selectedNumber by viewModel.selectedNumber.collectAsState(null)
    val permissionsState = rememberMultiplePermissionsState(AppDefaults.permissions)
    var allowUi by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        allowUi = permissions.all { it.value }
    }

    LaunchedEffect(permissionsState) {
        if(!permissionsState.allPermissionsGranted && permissionsState.shouldShowRationale) {
            Toast.makeText(context, "All permissions should be granted", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(AppDefaults.permissions.toTypedArray())
        }
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Row(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            if (allowUi) {
                ContactList(
                    modifier = Modifier.weight(0.3f),
                    onContactSelect = viewModel::selectClient
                )
                VerticalDivider()
                Box {
                    selectedNumber?.let { selectedNumber ->
                        SmsList(phoneNumber = selectedNumber)
                    }
                }
            }
        }
    }
}
