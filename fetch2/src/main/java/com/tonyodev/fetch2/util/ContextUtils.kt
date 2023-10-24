package com.tonyodev.fetch2.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Build

fun Context.registerBroadcastReceiver(broadcastReceiver: BroadcastReceiver, action: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            Context.RECEIVER_NOT_EXPORTED
        else 0
        registerReceiver(
            broadcastReceiver,
            IntentFilter(action),
            flags
        )
    }else {
        registerReceiver(
            broadcastReceiver,
            IntentFilter(action)
        )
    }
}