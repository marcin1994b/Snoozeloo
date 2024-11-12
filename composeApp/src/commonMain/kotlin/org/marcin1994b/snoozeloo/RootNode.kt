package org.marcin1994b.snoozeloo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import org.marcin1994b.snoozeloo.ui.alarmScreen.AlarmListNode
import org.marcin1994b.snoozeloo.ui.alarmTriggerScreen.AlarmTriggerNode
import org.marcin1994b.snoozeloo.ui.setAlarmScreen.SetAlarmNode
import org.marcin1994b.snoozeloo.ui.splashScreen.SplashNode

sealed class RootNodeNavTarget : Parcelable {
    @Parcelize
    data object SplashScreen : RootNodeNavTarget()

    @Parcelize
    data object AlarmListScreen : RootNodeNavTarget()

    @Parcelize
    data object SetAlarmScreen : RootNodeNavTarget()

    @Parcelize
    data object AlarmTriggerScreen: RootNodeNavTarget()
}

class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<RootNodeNavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = RootNodeNavTarget.SplashScreen,
            savedStateMap = nodeContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : Node<RootNodeNavTarget>(
    appyxComponent = backStack,
    nodeContext = nodeContext
) {

    override fun buildChildNode(navTarget: RootNodeNavTarget, nodeContext: NodeContext): Node<*> = when (navTarget) {
        RootNodeNavTarget.SplashScreen -> SplashNode(nodeContext)

        RootNodeNavTarget.AlarmListScreen -> AlarmListNode(nodeContext)

        RootNodeNavTarget.SetAlarmScreen -> SetAlarmNode(nodeContext)

        RootNodeNavTarget.AlarmTriggerScreen -> AlarmTriggerNode(nodeContext)
    }

    @Composable
    override fun Content(modifier: Modifier) {
        CompositionLocalProvider(*provideNullAndroidOverscrollConfiguration()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppyxNavigationContainer(
                    appyxComponent = backStack,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }

    override fun onChildFinished(child: Node<*>) {
        super.onChildFinished(child)
        backStack.pop()
    }

}