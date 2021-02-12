package com.th3pl4gu3.mes.ui.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.th3pl4gu3.mes.ui.utils.extensions.loadImageUrl

/*
* Loads a given url image to an ImageView
*/
@BindingAdapter("configureLogo")
fun ImageView.configureLogo(url: String) = loadImageUrl(
    url.toUri().buildUpon()?.scheme("https")?.build()
)


/*
* Formats a given MUR phone number
*/
@BindingAdapter("toMurPhoneNumberStringFormat")
fun TextView.toMurPhoneNumberStringFormat(number: Long) {
    this.text = number.toMruPhoneNumberString()
}

/*
* Loads a given icon
*/
@BindingAdapter("loadIcon")
fun ImageView.loadIcon(icon: Int) {
    this.setImageResource(icon)
}