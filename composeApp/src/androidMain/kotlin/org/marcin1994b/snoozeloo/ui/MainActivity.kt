package org.marcin1994b.snoozeloo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.marcin1994b.snoozeloo.RootNode
import org.marcin1994b.snoozeloo.schedulers.AndroidAlarmScheduler
import org.marcin1994b.snoozeloo.db.getDatabaseBuilder
import org.marcin1994b.snoozeloo.db.getRoomDatabase
import org.marcin1994b.snoozeloo.di.AppModule
import org.marcin1994b.snoozeloo.notifications.AlarmNotificationManager
import org.marcin1994b.snoozeloo.theme.AppTheme

class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val androidDI = DI {
            bindSingleton { getRoomDatabase(getDatabaseBuilder(applicationContext)) }
            bindSingleton { AndroidAlarmScheduler(applicationContext) }

            import(AppModule.diContainer)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // do nothing
        } else {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        AlarmNotificationManager(this.applicationContext).createAlarmChannel()

        setContent {
            MaterialTheme {
                AppTheme {
                    NodeHost(
                        lifecycle = AndroidLifecycle(LocalLifecycleOwner.current.lifecycle),
                        integrationPoint = appyxV2IntegrationPoint,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        RootNode(
                            nodeContext = it,
                            di = androidDI,
                        )
                    }
                }
            }
        }
    }
}