package dev.nordix.smsaccessor.common_ui

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun NordixIconButton(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit
){
    IconButton(onClick) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

@Composable
fun NordixIconButton(
    @DrawableRes drawableResId: Int,
    contentDescription: String? = null,
    onClick: () -> Unit
){
    IconButton(onClick) {
        Icon(painter = painterResource(drawableResId), contentDescription = contentDescription)
    }
}
