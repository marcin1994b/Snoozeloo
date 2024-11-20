package org.marcin1994b.snoozeloo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.bumble.appyx.navigation.integration.IosNodeHost
import com.bumble.appyx.navigation.integration.MainIntegrationPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.theme.AppTheme


val backEvents: Channel<Unit> = Channel()

private val integrationPoint = MainIntegrationPoint()

fun MainViewController() = ComposeUIViewController {
    val database = remember {  getDatabaseBuilder() }

    disableUiKitOverscroll()
    AppTheme {
        IosNodeHost(
            modifier = Modifier.fillMaxSize(),
            onBackPressedEvents = backEvents.receiveAsFlow(),
            integrationPoint = remember { integrationPoint }
        ) {
            RootNode(
                nodeContext = it,
                appDatabaseBuilder = database
            )
        }
    }
}