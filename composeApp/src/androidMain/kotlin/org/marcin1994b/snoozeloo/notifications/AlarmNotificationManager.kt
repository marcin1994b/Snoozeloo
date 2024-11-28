package org.marcin1994b.snoozeloo.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.PowerManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.marcin1994b.snoozeloo.ACTION_CANCEL
import org.marcin1994b.snoozeloo.ACTION_SNOOZE
import org.marcin1994b.snoozeloo.ui.AlarmTriggeredActivity
import org.marcin1994b.snoozeloo.EXTRAS_ALARM_ID
import org.marcin1994b.snoozeloo.R
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.receivers.AlarmReceiver

class AlarmNotificationManager(private val applicationContext: Context) {

    private val ALARM_NOTIFICATION_CHANNEL_ID = "ALARM_NOTIFICATION_CHANNEL_ID"
    private val ALARM_NOTIFICATION_CHANNEL_NAME = "ALARM_NOTIFICATION_CHANNEL"

    private val notificationManager = NotificationManagerCompat.from(applicationContext)

    fun sendAlarmNotification(alarm: Alarm) {
        asquireWake()

        val fullScreenPendingIntent = getFullScreenPendingIntent(alarm)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, ALARM_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(alarm.name)
            .setContentText(applicationContext.getString(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setSound(Uri.EMPTY)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(
                R.drawable.ic_alarm_snooze,
                applicationContext.getString(R.string.notification_action_snooze),
                getSnoozeActionPendingIntent(alarm)
            )
            .addAction(
                R.drawable.ic_alarm_cancel,
                applicationContext.getString(R.string.notification_action_cancel),
                getCancelActionPendingIntent(alarm)
            )
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(fullScreenPendingIntent)
            .setFullScreenIntent(fullScreenPendingIntent, true)

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(alarm.id, notificationBuilder.build())
        }

    }

    fun createAlarmChannel() {
        NotificationChannel(
            ALARM_NOTIFICATION_CHANNEL_ID,
            ALARM_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).let { channel ->
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun cancelNotification(alarmId: String) {
        notificationManager.cancel(alarmId.toInt())
    }

    private fun asquireWake() {
        val mPowerManager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val mWakeLock: PowerManager.WakeLock = mPowerManager.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "YourApp:Whatever"
        )
        mWakeLock.acquire()
    }

    private fun getSnoozeActionPendingIntent(alarm: Alarm) = PendingIntent.getBroadcast(
        applicationContext,
        alarm.id.hashCode(),
        Intent(applicationContext, AlarmReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRAS_ALARM_ID, alarm.id.toString())
        },
        PendingIntent.FLAG_IMMUTABLE
    )

    private fun getCancelActionPendingIntent(alarm: Alarm) = PendingIntent.getBroadcast(
        applicationContext,
        alarm.id.hashCode(),
        Intent(applicationContext, AlarmReceiver::class.java).apply {
            action = ACTION_CANCEL
            putExtra(EXTRAS_ALARM_ID, alarm.id.toString())
        },
        PendingIntent.FLAG_IMMUTABLE
    )

    private fun getFullScreenPendingIntent(alarm: Alarm) = PendingIntent.getActivity(
        applicationContext,
        111111,
        Intent(applicationContext, AlarmTriggeredActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRAS_ALARM_ID, alarm.id.toString())
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

}