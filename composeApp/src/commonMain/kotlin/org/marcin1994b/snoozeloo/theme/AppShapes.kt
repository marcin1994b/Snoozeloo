package org.marcin1994b.snoozeloo.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class AppShapes(
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val medium: CornerBasedShape = RoundedCornerShape(8.dp),
    val large: CornerBasedShape = RoundedCornerShape(12.dp),
    val none: CornerBasedShape = RoundedCornerShape(0),
    val full: CornerBasedShape = RoundedCornerShape(50)
)

val LocalAppShapes = staticCompositionLocalOf { AppShapes() }
