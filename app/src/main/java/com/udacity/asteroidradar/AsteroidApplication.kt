package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.work.DeleteDataWorkManager
import com.udacity.asteroidradar.work.RefreshDataWorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayInit()
    }

    private fun delayInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        //refresh
        val repeatingRefreshingRequest = PeriodicWorkRequestBuilder<RefreshDataWorkManager>(
            1,
            TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork("Refresh_Work",
        ExistingPeriodicWorkPolicy.KEEP,
            repeatingRefreshingRequest)

        //delete
        val repeatingDeletingRequest = PeriodicWorkRequestBuilder<DeleteDataWorkManager>(
            1,
            TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork("Delete_Work",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingDeletingRequest)
    }
}