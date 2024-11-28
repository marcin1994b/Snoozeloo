package org.marcin1994b.snoozeloo.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.marcin1994b.snoozeloo.EXTRAS_ALARM_ID
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.db.getRoomDatabase
import org.marcin1994b.snoozeloo.di.AppModule
import org.marcin1994b.snoozeloo.schedulers.AndroidAlarmScheduler
import org.marcin1994b.snoozeloo.theme.AppTheme
import org.marcin1994b.snoozeloo.ui.alarmTriggerScreen.AlarmTriggerNode

class AlarmTriggeredActivity : NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var alarmId: String? = null

        if (intent.hasExtra(EXTRAS_ALARM_ID)) {
            alarmId = intent.getStringExtra(EXTRAS_ALARM_ID)
        } else {
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        alarmId?.let {
            setContent {
                MaterialTheme {
                    AppTheme {
                        NodeHost(
                            lifecycle = AndroidLifecycle(LocalLifecycleOwner.current.lifecycle),
                            integrationPoint = appyxV2IntegrationPoint,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AlarmTriggerNode(
                                nodeContext = it,
                                alarmId = alarmId,
                                di = DI {
                                    bindSingleton { getRoomDatabase(getDatabaseBuilder(applicationContext)) }
                                    bindSingleton { AndroidAlarmScheduler(applicationContext) }

                                    import(AppModule.diContainer)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}