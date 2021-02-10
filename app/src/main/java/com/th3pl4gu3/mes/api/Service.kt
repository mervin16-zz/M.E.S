package com.th3pl4gu3.mes.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    val identifier: String,
    val name: String,
    val type: String,
    val icon: String,
    val number: Long
) : Parcelable