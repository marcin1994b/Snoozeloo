package org.marcin1994b.snoozeloo.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.alarmScheduler.AlarmScheduler
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.db.getRoomDatabase
import org.marcin1994b.snoozeloo.di.AppModule
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult
import org.marcin1994b.snoozeloo.schedulers.AndroidAlarmScheduler
import kotlin.coroutines.EmptyCoroutineContext

class BootCompletedReceiver : BroadcastReceiver(), DIAware {

    override lateinit var di: DI

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {

                di = DI {
                    bindSingleton { getRoomDatabase(getDatabaseBuilder(context.applicationContext)) }
                    bindSingleton { AndroidAlarmScheduler(context.applicationContext) }

                    import(AppModule.diContainer)
                }

                val alarmDatabaseInteractor: AlarmDatabaseInteractor by instance()
                val alarmScheduler: AlarmScheduler by instance()

                GlobalScope.launch(EmptyCoroutineContext) {
                    val result = alarmDatabaseInteractor.getAllAlarms()

                    if (result is AlarmDatabaseResult.GetAllSuccess) {
                        result.list.forEach { alarm ->
                            if (alarm.isOn) {
                                alarmScheduler.schedule(alarm)
                            }
                        }
                    }
                }
            }
        } ?: run {

        }
    }
}