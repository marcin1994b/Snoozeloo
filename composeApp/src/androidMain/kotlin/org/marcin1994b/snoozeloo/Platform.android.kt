package org.marcin1994b.snoozeloo

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.ProvidedValue

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun disableUiKitOverscroll() {}
@OptIn(ExperimentalFoundationApi::class)
actual fun provideNullAndroidOverscrollConfiguration(): Array<ProvidedValue<*>> = arrayOf(
    LocalOverscrollConfiguration provides null
)
