package dev.nordix.smsaccessor.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.nordix.smsaccessor.common_ui.helpers.toHslColor

@Composable
fun ClientIcon(
    modifier: Modifier = Modifier,
    name: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    size: Dp = IMAGE_SIZE,
) {

    val nameGroups = name.split(" ")
    val abbreviatedName = if (nameGroups.size > 1){
        nameGroups.first().take(1) + nameGroups.last().take(1)
    } else {
        name.take(2).uppercase()
    }.uppercase()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(
                color = Color(abbreviatedName.toHslColor()),
                shape = CircleShape
            ),
    ) {
        Text(
            text = abbreviatedName,
            style = textStyle.copy(fontSize = textStyle.fontSize * (size/IMAGE_SIZE)),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }

}

private val IMAGE_SIZE = 48.dp
