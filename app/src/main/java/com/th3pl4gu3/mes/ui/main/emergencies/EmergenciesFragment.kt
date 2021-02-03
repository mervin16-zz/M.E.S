package com.th3pl4gu3.mes.ui.main.emergencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentEmergenciesBinding

class EmergenciesFragment : Fragment() {

    private var mBinding: FragmentEmergenciesBinding? = null
    private var mViewModel: EmergenciesViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEmergenciesBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(EmergenciesViewModel::class.java)
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}