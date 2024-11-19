package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.design.AppFloatingActionButton
import org.marcin1994b.snoozeloo.theme.AppColors

class AlarmListNode(
    nodeContext: NodeContext,
    override val di: DI
) : LeafNode(nodeContext), DIAware {

    private val viewModel: AlarmListViewModel by instance()

    @Composable
    override fun Content(modifier: Modifier) {
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        val viewState = viewModel.alarmListData.value

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppColors.Grey075,
            floatingActionButton = {
                AppFloatingActionButton(
                    onClick = {}
                )
            }
        ) {
            when (viewState) {
                AlarmListViewState.Loading -> {}
                AlarmListViewState.Empty -> AlarmListEmptyStateView()
                is AlarmListViewState.Loaded -> AlarmListLoadedStateView(viewState.alarms)
            }
        }
    }
}