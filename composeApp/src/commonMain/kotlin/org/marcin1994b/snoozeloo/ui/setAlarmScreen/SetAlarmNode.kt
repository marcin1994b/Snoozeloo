package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.design.AppTopBar
import org.marcin1994b.snoozeloo.theme.AppColors

class SetAlarmNode(
    nodeContext: NodeContext,
    override val di: DI,
    private val alarmId: Int?,
) : LeafNode(nodeContext), DIAware {

    private val viewModel: SetAlarmViewModel by instance<SetAlarmViewModel>()

    @Composable
    override fun Content(modifier: Modifier) {

        viewModel.initViewData(alarmId)

        val scaffoldState: ScaffoldState = rememberScaffoldState()

        LaunchedEffect(key1 = viewModel.addedSuccessfully.value) {
            if (viewModel.addedSuccessfully.value) {
                finish()
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppColors.Grey075,
            topBar = {
                AppTopBar(
                    title = "Add New Alarm",
                    onBackButtonPress = { finish() }
                )
            }
        ) {
            viewModel.alarmData.value?.let {
                SetAlarmScreen(
                    alarmData = it,
                    onSaveButtonClick = { alarm ->
                        viewModel.saveAlarm(alarm)
                    }
                )
            }
        }
    }
}

