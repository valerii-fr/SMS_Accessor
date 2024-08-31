package dev.nordix.smsaccessor.presentation.app

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.nordix.smsaccessor.presentation.app.navigation.AppNavigation

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SmsAccessorApp(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
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

    if (allowUi) AppNavigation(modifier)
}
