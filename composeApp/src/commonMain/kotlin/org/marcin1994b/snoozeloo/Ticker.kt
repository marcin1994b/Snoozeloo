package org.marcin1994b.snoozeloo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun CoroutineScope.Ticker(
    tickInMillis: Long,
    onTick: () -> Unit
) {
    this.launch(Dispatchers.Default) {
        while (true) {
            withContext(Dispatchers.Main) { onTick() }
            delay(tickInMillis)
        }
    }
}