package org.marcin1994b.snoozeloo.feedback

import kotlinx.coroutines.flow.MutableSharedFlow

object AppFeedback {
    val data: MutableSharedFlow<AppFeedbackMsg?> = MutableSharedFlow()
}