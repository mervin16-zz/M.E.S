package com.th3pl4gu3.mes.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var mBinding: FragmentAboutBinding? = null
    private var mViewModel: AboutViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAboutBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }
}