package com.th3pl4gu3.mes.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var mBinding: FragmentSettingsBinding? = null
    private var mViewModel: SettingsViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }
}