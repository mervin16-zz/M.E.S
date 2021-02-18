package com.th3pl4gu3.mes.ui.utils.extensions

import android.content.Intent
import android.net.Uri
import com.th3pl4gu3.mes.BuildConfig
import android.provider.Settings

/**
 * This file represents extensions for all
 * Android Intents
 */

// Returns an Action Call Intent
inline val String.getPhoneCallIntent: Intent
    get() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$this")
        return intent
    }

// Returns Application Settings Intent
fun requireAppSettingsIntent() = with(Intent()) {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts(
        "package",
        BuildConfig.APPLICATION_ID,
        null
    )
    data = uri
    flags = Intent.FLAG_ACTIVITY_NEW_TASK

    this
}

