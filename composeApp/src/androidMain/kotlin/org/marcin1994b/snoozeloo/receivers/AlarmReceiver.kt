package org.marcin1994b.snoozeloo.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.ACTION_CANCEL
import org.marcin1994b.snoozeloo.ACTION_SHOW_NOTIFICATION
import org.marcin1994b.snoozeloo.ACTION_SNOOZE
import org.marcin1994b.snoozeloo.EXTRAS_ALARM_ID
import org.marcin1994b.snoozeloo.alarmScheduler.AlarmScheduler
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.db.getRoomDatabase
import org.marcin1994b.snoozeloo.di.AppModule
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult
import org.marcin1994b.snoozeloo.notifications.AlarmNotificationManager
import org.marcin1994b.snoozeloo.schedulers.AndroidAlarmScheduler
import kotlin.coroutines.EmptyCoroutineContext

class AlarmReceiver: BroadcastReceiver(), DIAware {

    override lateinit var di: DI

    private val alarmDatabaseInteractor: AlarmDatabaseInteractor by instance()
    private val alarmScheduler: AlarmScheduler by instance()

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val alarmId = intent?.getStringExtra(EXTRAS_ALARM_ID) ?: return

            initDI(context)

            when (intent.action) {
                ACTION_SHOW_NOTIFICATION -> onShowNotification(context, alarmId)

                ACTION_SNOOZE -> onSnooze(context, alarmId)

                ACTION_CANCEL -> onCancel(context, alarmId)
            }
        }
    }

    private fun initDI(context: Context) {
        if (!this::di.isInitialized) {
            di = DI {
                bindSingleton { getRoomDatabase(getDatabaseBuilder(context.applicationContext)) }
                bindSingleton { AndroidAlarmScheduler(context.applicationContext) }

                import(AppModule.diContainer)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onShowNotification(context: Context, alarmId: String) {
        GlobalScope.launch(EmptyCoroutineContext) {
            val result = alarmDatabaseInteractor.getAlarmById(alarmId.toLong())

            if (result is AlarmDatabaseResult.GetAllSuccess) {
                AlarmNotificationManager(context).sendAlarmNotification(result.list.first())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onSnooze(context: Context, alarmId: String) {
        GlobalScope.launch(EmptyCoroutineContext) {
            val result = alarmDatabaseInteractor.getAlarmById(alarmId.toLong())

            if (result is AlarmDatabaseResult.GetAllSuccess) {
                alarmScheduler.snooze(result.list.first())
            }

            AlarmNotificationManager(context).cancelNotification(alarmId)
        }
    }

    private fun onCancel(context: Context, alarmId: String) {
        AlarmNotificationManager(context.applicationContext).cancelNotification(alarmId)
    }
}