package com.th3pl4gu3.mes.ui.utils.extensions

import java.util.*

fun String.lowercase() = this.toLowerCase(Locale.getDefault())

fun String.join(piece: String) = String.format(this, piece)