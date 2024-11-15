package org.marcin1994b.snoozeloo.ui.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.jetbrains.compose.resources.painterResource
import org.marcin1994b.snoozeloo.theme.AppColors
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.alarm_icon

class SplashNode(nodeContext: NodeContext) : LeafNode(nodeContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = AppColors.BrandBlue),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.alarm_icon),
                contentDescription = null
            )
        }
    }
}