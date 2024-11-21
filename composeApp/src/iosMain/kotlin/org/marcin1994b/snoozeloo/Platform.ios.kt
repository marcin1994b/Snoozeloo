package org.marcin1994b.snoozeloo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.optOutOfCupertinoOverscroll
import androidx.compose.runtime.ProvidedValue
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = "iOS"
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalFoundationApi::class)
actual fun disableUiKitOverscroll() = optOutOfCupertinoOverscroll()
actual fun provideNullAndroidOverscrollConfiguration() = emptyArray<ProvidedValue<*>>()
