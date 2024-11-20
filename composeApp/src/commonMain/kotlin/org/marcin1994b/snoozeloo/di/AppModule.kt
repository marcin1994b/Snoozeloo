package org.marcin1994b.snoozeloo.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.model.GetAlarms
import org.marcin1994b.snoozeloo.ui.alarmListScreen.AlarmListViewModel
import org.marcin1994b.snoozeloo.ui.setAlarmScreen.SetAlarmViewModel

object AppModule {
    val diContainer = DI.Module("AppModule") {

        bindProvider { GetAlarms() }

        bindSingleton { AlarmListViewModel(instance()) }
        bindSingleton { SetAlarmViewModel(instance()) }
    }
}