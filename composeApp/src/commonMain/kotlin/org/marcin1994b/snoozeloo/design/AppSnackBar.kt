package org.marcin1994b.snoozeloo.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.marcin1994b.snoozeloo.theme.AppTheme
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun rememberSnackState(): SnackState {
    return remember { SnackState() }
}

@Composable
fun SnackError(
    state: SnackState,
    duration: Long = 3000L,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SnackComponent(
            state = state,
            duration = duration,
            containerColor = BrightRed,
            contentColor = TextWhite,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp,
            icon = Icons.Default.Warning
        )
    }
}

@Composable
fun SnackSuccess(
    state: SnackState,
    duration: Long = 3000L,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SnackComponent(
            state = state,
            duration = duration,
            containerColor = BrightBlue,
            contentColor = TextWhite,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp,
            icon = Icons.Default.Check
        )
    }
}

@Composable
internal fun SnackComponent(
    state: SnackState,
    duration: Long,
    containerColor: Color,
    contentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector
) {
    var showSnack by remember { mutableStateOf(false) }
    val message by rememberUpdatedState(newValue = state.message.value)

    DisposableEffect(
        key1 = state.updateState
    ) {
        showSnack = true

        val scope = CoroutineScope(EmptyCoroutineContext).launch {
            delay(duration)
            showSnack = false
        }

        onDispose {
            scope.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 92.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = state.isNotEmpty() && showSnack,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Snack(
                message,
                containerColor,
                contentColor,
                verticalPadding,
                horizontalPadding,
                icon
            )
        }
    }
}

@Composable
internal fun Snack(
    message: StringResource?,
    containerColor: Color,
    contentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(
                fraction = 0.8f // position
            )
            .background(
                color = containerColor,
                shape = AppTheme.shapes.full
            )
            .padding(vertical = verticalPadding)
            .padding(horizontal = horizontalPadding)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            message?.let {
                Text(
                    text = stringResource(it),
                    color = contentColor,
                    style = AppTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

        }
    }
}

class SnackState {

    private val _message = mutableStateOf<StringResource?>(null)
    val message: State<StringResource?> = _message

    var updateState by  mutableStateOf(false)
        private set

    fun addMessage(text: StringResource) {
        _message.value = text
        updateState = !updateState
    }

    fun isNotEmpty(): Boolean {
        return _message.value != null
    }

}

val BrightBlue = Color(0xff4664FF)
val BrightRed = Color(0xFFE8503A)
val TextWhite = Color(0xFFEEEEEE)
