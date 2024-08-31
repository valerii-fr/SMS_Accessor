package dev.nordix.smsaccessor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.nordix.smsaccessor.presentation.app.SmsAccessorApp
import dev.nordix.smsaccessor.ui.theme.SMSAccessorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMSAccessorTheme {
                SmsAccessorApp(Modifier.fillMaxSize())
            }
        }
    }
}
