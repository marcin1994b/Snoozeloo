package org.marcin1994b.snoozeloo.ui.splashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.marcin1994b.snoozeloo.theme.AppTheme

class SplashNode(nodeContext: NodeContext) : LeafNode(nodeContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Splash Screen View",
                color = AppTheme.colorScheme.error
            )
        }
    }
}