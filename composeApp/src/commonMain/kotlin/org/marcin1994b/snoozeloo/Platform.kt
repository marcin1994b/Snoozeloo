package org.marcin1994b.snoozeloo

import androidx.compose.runtime.ProvidedValue

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun disableUiKitOverscroll()
expect fun provideNullAndroidOverscrollConfiguration(): Array<ProvidedValue<*>>
