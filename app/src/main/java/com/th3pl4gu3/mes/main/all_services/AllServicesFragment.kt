package com.th3pl4gu3.mes.main.all_services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentAllServicesBinding

class AllServicesFragment : Fragment() {

    private var mBinding: FragmentAllServicesBinding? = null
    private var mViewModel: AllServicesViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAllServicesBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(AllServicesViewModel::class.java)
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}