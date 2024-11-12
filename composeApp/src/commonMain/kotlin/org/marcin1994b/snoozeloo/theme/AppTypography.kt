package org.marcin1994b.snoozeloo.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.montserrat_thin

data class AppTypography(
    val headline1: TextStyle = TextStyle(
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
        lineHeight = 112.sp
    ),
    val headline2: TextStyle = TextStyle(
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 72.sp
    ),
    val headline3: TextStyle = TextStyle(
        fontSize = 48.sp,
        letterSpacing = 0.sp,
        lineHeight = 56.sp
    ),
    val headline4: TextStyle = TextStyle(
        fontSize = 34.sp,
        letterSpacing = 0.sp,
        lineHeight = 36.sp
    ),
    val headline5: TextStyle = TextStyle(
        fontSize = 24.sp,
        letterSpacing = 0.18.sp,
        lineHeight = 24.sp
    ),
    val headline6: TextStyle = TextStyle(
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    val body1: TextStyle = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.25.sp,
        lineHeight = 22.sp
    ),
    val body2: TextStyle = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        lineHeight = 18.sp
    ),
    val subtitle1: TextStyle = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    val subtitle2: TextStyle = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        lineHeight = 24.sp
    ),
    val caption1: TextStyle = TextStyle(
        fontSize = 12.sp,
        letterSpacing = 0.25.sp,
        lineHeight = 16.sp
    ),
    val caption2: TextStyle = TextStyle(
        fontSize = 11.sp,
        letterSpacing = (-1.5).sp,
        lineHeight = 14.sp
    ),
    val button1: TextStyle = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.75.sp,
        lineHeight = 20.sp
    ),
    val button2: TextStyle = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.75.sp,
        lineHeight = 16.sp
    ),
) {

    val light = SpanStyle(
        fontWeight = FontWeight.Light
    )

    val bold = SpanStyle(
        fontWeight = FontWeight.Bold
    )

    val semiBold = SpanStyle(
        fontWeight = FontWeight.SemiBold
    )

    val medium = SpanStyle(
        fontWeight = FontWeight.Medium
    )

}

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
