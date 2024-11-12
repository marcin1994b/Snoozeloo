package org.marcin1994b.snoozeloo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

private val LightColorPalette = lightColors(
    primary = AppColors.BrandBlue,
    primaryVariant = AppColors.White,
    secondary = AppColors.BrandBlue,
    secondaryVariant = AppColors.White,
    background = AppColors.White,
    surface = AppColors.White,
    error = AppColors.Red500,
    onPrimary = AppColors.White,
    onSecondary = AppColors.White,
    onBackground = AppColors.Black,
    onSurface = AppColors.Black,
    onError = AppColors.White,
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        LightColorPalette // TODO
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )

}


object AppTheme {

    val colorScheme: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

}
