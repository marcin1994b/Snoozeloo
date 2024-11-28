package org.marcin1994b.snoozeloo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.marcin1994b.snoozeloo.design.SnackError
import org.marcin1994b.snoozeloo.design.SnackState
import org.marcin1994b.snoozeloo.design.SnackSuccess
import org.marcin1994b.snoozeloo.design.rememberSnackState
import org.marcin1994b.snoozeloo.feedback.AppFeedback
import org.marcin1994b.snoozeloo.feedback.AppFeedbackMsg
import org.marcin1994b.snoozeloo.feedback.toStringRes
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.ui.alarmListScreen.AlarmListNode
import org.marcin1994b.snoozeloo.ui.setAlarmScreen.SetAlarmNode
import org.marcin1994b.snoozeloo.ui.splashScreen.SplashNode

sealed class RootNodeNavTarget : Parcelable {
    @Parcelize
    data object SplashScreen : RootNodeNavTarget()

    @Parcelize
    data object AlarmListScreen : RootNodeNavTarget()

    @Parcelize
    data class SetAlarmScreen(
        val alarmId: Int?
    ) : RootNodeNavTarget()
}

class RootNode(
    nodeContext: NodeContext,
    override val di: DI,
    private val backStack: BackStack<RootNodeNavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = RootNodeNavTarget.AlarmListScreen,
            savedStateMap = nodeContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : Node<RootNodeNavTarget>(
    appyxComponent = backStack,
    nodeContext = nodeContext
), DIAware {

    override fun buildChildNode(navTarget: RootNodeNavTarget, nodeContext: NodeContext): Node<*> = when (navTarget) {
        RootNodeNavTarget.SplashScreen -> SplashNode(nodeContext)

        RootNodeNavTarget.AlarmListScreen -> AlarmListNode(
            nodeContext = nodeContext,
            di = di,
            onAddNewAlarmClick = {
                backStack.push(RootNodeNavTarget.SetAlarmScreen(alarmId = null))
            },
            onAlarmItemClick = {
                backStack.push(RootNodeNavTarget.SetAlarmScreen(alarmId = it.id))
            }
        )

        is RootNodeNavTarget.SetAlarmScreen -> SetAlarmNode(
            nodeContext = nodeContext,
            di = di,
            alarmId = navTarget.alarmId
        )
    }

    @Composable
    override fun Content(modifier: Modifier) {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val snackSuccessState: SnackState = rememberSnackState()
        val snackErrorState: SnackState = rememberSnackState()

        LaunchedEffect(true) {
            AppFeedback.data.collectLatest {
                when (it) {
                    is AppFeedbackMsg.AppFeedbackPositiveMsg -> {
                        it.toStringRes()?.let { resMsg -> snackSuccessState.addMessage(resMsg) }
                    }

                    is AppFeedbackMsg.AppFeedbackNegativeMsg -> {
                        it.toStringRes()?.let { resMsg -> snackErrorState.addMessage(resMsg) }
                    }

                    else -> {

                    }
                }
            }
        }

        CompositionLocalProvider(*provideNullAndroidOverscrollConfiguration()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                backgroundColor = AppColors.Grey075,
            ) {
                AppyxNavigationContainer(
                    appyxComponent = backStack,
                    modifier = Modifier.fillMaxSize()
                )

                SnackSuccess(snackSuccessState)
                SnackError(snackErrorState)
            }
        }

    }

    override fun onChildFinished(child: Node<*>) {
        super.onChildFinished(child)
        backStack.pop()
    }

}