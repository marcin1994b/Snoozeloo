package org.marcin1994b.snoozeloo.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object AppColors {
    val BrandBlue = Color(0xff4664FF)
    val Black = Color(0xff0d0f19)
    val White = Color(0xffffffff)
    val Grey050 = Color(0xfffbfbfb)
    val Grey060 = Color(0xfff9f9f9)
    val Grey075 = Color(0xfff4f5f5)
    val Grey100 = Color(0xffedeff0)
    val Grey200 = Color(0xffdee1e3)
    val Grey250 = Color(0xffd5d8d9)
    val Grey300 = Color(0xffbec2c4)
    val Grey400 = Color(0xffa8abad)
    val Grey500 = Color(0xff7b7d7f)
    val Grey600 = Color(0xff636466)
    val Grey700 = Color(0xff4a4b4d)
    val Grey800 = Color(0xff2d2d2e)

    val Red400 = Color(0xffe4002b)
    val Red500 = Color(0xffd60027)
    val Red600 = Color(0xffb21107)
    val Red800 = Color(0xffa52312)

}

val LocalAppColors = staticCompositionLocalOf { lightColors() }
