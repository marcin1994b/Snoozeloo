package org.marcin1994b.snoozeloo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.bumble.appyx.navigation.integration.IosNodeHost
import com.bumble.appyx.navigation.integration.MainIntegrationPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.marcin1994b.snoozeloo.alarmScheduler.IosAlarmScheduler
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.db.getRoomDatabase
import org.marcin1994b.snoozeloo.di.AppModule
import org.marcin1994b.snoozeloo.theme.AppTheme


val backEvents: Channel<Unit> = Channel()

private val integrationPoint = MainIntegrationPoint()

fun MainViewController() = ComposeUIViewController {
    val database = remember {  getDatabaseBuilder() }

    val iosDI = DI {
        bindSingleton { getRoomDatabase(database) }
        bindSingleton { IosAlarmScheduler() }

        import(AppModule.diContainer)
    }

    disableUiKitOverscroll()
    AppTheme {
        IosNodeHost(
            modifier = Modifier.fillMaxSize(),
            onBackPressedEvents = backEvents.receiveAsFlow(),
            integrationPoint = remember { integrationPoint }
        ) {
            RootNode(
                nodeContext = it,
                di = iosDI
            )
        }
    }
}