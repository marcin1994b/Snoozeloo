package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        LaunchedEffect(key1 = viewModel.finishView.value) {
            if (viewModel.finishView.value) {
                finish()
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppColors.Grey075,
            topBar = {
                AppTopBar(
                    title = "Add New Alarm",
                    onBackButtonPress = { finish() },
                    icon = {
                        viewModel.alarmData.value?.let {
                            if (it.id != 0) {
                                Icon(
                                    modifier = Modifier.size(32.dp).clickable {
                                        viewModel.deleteAlarm()
                                    },
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = AppColors.BrandBlue
                                )
                            }
                        }
                    }
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

