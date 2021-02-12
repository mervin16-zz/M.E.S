package com.th3pl4gu3.mes.ui.utils.extensions

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

/*
* Get string resources directly from within
* a view model
*/
inline val AndroidViewModel.applicationContext: Context get() = getApplication<Application>().applicationContext

fun AndroidViewModel.getString(res: Int) = getApplication<Application>().getString(res)