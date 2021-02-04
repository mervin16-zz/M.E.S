package com.th3pl4gu3.mes.ui.utils.helpers

import android.widget.ImageView
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