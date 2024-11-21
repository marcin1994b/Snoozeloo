package org.marcin1994b.snoozeloo.feedback

import org.jetbrains.compose.resources.StringResource
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.something_went_wrong
import snoozeloo.composeapp.generated.resources.set_alarm_added_successfully_msg
import snoozeloo.composeapp.generated.resources.set_alarm_updated_successfully_msg
import snoozeloo.composeapp.generated.resources.set_alarm_deleted_successfully_msg

sealed interface AppFeedbackMsg {
    sealed interface AppFeedbackPositiveMsg: AppFeedbackMsg {
        data object AlarmAddedSuccessfully: AppFeedbackPositiveMsg
        data object AlarmUpdatedSuccessfully: AppFeedbackPositiveMsg
        data object AlarmDeletedSuccessfully: AppFeedbackPositiveMsg
    }

    sealed interface AppFeedbackNegativeMsg: AppFeedbackMsg {
        data object AlarmAddingFailed: AppFeedbackNegativeMsg
        data object AlarmUpdatingFailed: AppFeedbackNegativeMsg
        data object AlarmDeletingFailed: AppFeedbackNegativeMsg
    }
}

fun AppFeedbackMsg.toStringRes(): StringResource? = when (this) {
    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmAddedSuccessfully -> Res.string.set_alarm_added_successfully_msg
    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmUpdatedSuccessfully -> Res.string.set_alarm_updated_successfully_msg
    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmDeletedSuccessfully -> Res.string.set_alarm_deleted_successfully_msg

    AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmAddingFailed,
    AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmUpdatingFailed,
    AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmDeletingFailed -> Res.string.something_went_wrong

    else -> null
}