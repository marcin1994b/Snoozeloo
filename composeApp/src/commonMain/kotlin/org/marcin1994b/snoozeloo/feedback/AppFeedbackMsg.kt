package org.marcin1994b.snoozeloo.feedback

import org.jetbrains.compose.resources.StringResource
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.set_alarm_failed_msg
import snoozeloo.composeapp.generated.resources.set_alarm_success_msg
import snoozeloo.composeapp.generated.resources.set_alarm_update_msg

sealed interface AppFeedbackMsg {
    sealed interface AppFeedbackPositiveMsg: AppFeedbackMsg {
        data object AlarmAddedSuccessfully: AppFeedbackPositiveMsg
        data object AlarmUpdatedSuccessfully: AppFeedbackPositiveMsg
    }

    sealed interface AppFeedbackNegativeMsg: AppFeedbackMsg {
        data object AlarmAddingFailed: AppFeedbackNegativeMsg
    }
}



fun AppFeedbackMsg.toStringRes(): StringResource? = when (this) {
    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmAddedSuccessfully -> Res.string.set_alarm_success_msg
    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmUpdatedSuccessfully -> Res.string.set_alarm_update_msg

    AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmAddingFailed -> Res.string.set_alarm_failed_msg

    else -> null
}