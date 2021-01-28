package com.th3pl4gu3.mes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.th3pl4gu3.mes.databinding.ActivityMesBinding
import com.th3pl4gu3.mes.extensions.contentView

class MesActivity : AppCompatActivity() {

    private val mBinding: ActivityMesBinding by contentView(R.layout.activity_mes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mes)
    }
}