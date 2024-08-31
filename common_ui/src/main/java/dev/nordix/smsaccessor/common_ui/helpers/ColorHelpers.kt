package dev.nordix.smsaccessor.common_ui.helpers

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils

@ColorInt
fun String.toHslColor(
    @FloatRange(from = 0.0, to = 1.0) saturation: Float = 0.5f,
    @FloatRange(from = 0.0, to = 1.0) lightness: Float = 0.5f
): Int {
    val code = this.fold(0) { code, char ->  char.code + ((code shl 5) - code) }
    val hue = (code % 360).toFloat()
    return ColorUtils.HSLToColor(floatArrayOf(hue, saturation, lightness))
}
