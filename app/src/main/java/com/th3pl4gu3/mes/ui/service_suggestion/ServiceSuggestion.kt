package com.th3pl4gu3.mes.ui.service_suggestion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.FragmentSuggestServiceBinding
import com.th3pl4gu3.mes.ui.utils.extensions.hasSuccessor
import com.th3pl4gu3.mes.ui.utils.extensions.join
import com.th3pl4gu3.mes.ui.utils.extensions.requireMailIntent

class ServiceSuggestion : Fragment() {

    private var mBinding: FragmentSuggestServiceBinding? = null
    private var mViewModel: ServiceSuggestionViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSuggestServiceBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ServiceSuggestionViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.formValid.observe(viewLifecycleOwner, {
            if (it) {
                val emailConstruct = constructEmail()
                val intent = requireMailIntent(emailConstruct.first, emailConstruct.second)
                if (intent.hasSuccessor(requireContext())) {
                    startActivity(intent)
                } else {
                    // TODO("Show a prompt")
                    Log.v("INTENT_TEST", "No app found")
                }
            }
        })
    }

    private fun constructEmail(): Pair<String, String> {
        val subject = String.format(
            getString(R.string.email_subject_service_suggestion),
            viewModel.serviceName,
            viewModel.serviceNumber
        )
        val message = getString(R.string.email_body_service_suggestion).join(viewModel.serviceProof)

        return Pair(subject, message)
    }
}