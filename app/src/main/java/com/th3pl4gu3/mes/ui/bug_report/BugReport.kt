package com.th3pl4gu3.mes.ui.bug_report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentBugReportBinding
import com.th3pl4gu3.mes.ui.utils.extensions.hasSuccessor
import com.th3pl4gu3.mes.ui.utils.extensions.requireMailIntent

class BugReport : Fragment() {

    private var mBinding: FragmentBugReportBinding? = null
    private var mViewModel: BugReportViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBugReportBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(BugReportViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeListeners()
    }

    private fun subscribeListeners() {
        // Submit button
        binding.ButtonSubmit.setOnClickListener {
            // TODO("Check for data validity")

            val emailConstruct = constructEmail()
            val intent = requireMailIntent(emailConstruct.first, emailConstruct.second)
            if (intent.hasSuccessor(requireContext())) {
                startActivity(intent)
            } else {
                // TODO("Show a prompt")
                Log.v("INTENT_TEST", "No app found")
            }
        }
    }

    private fun constructEmail(): Pair<String, String> {
        val subject = "MES :: Bug Report :: ${viewModel.bugIdentified}"
        val message = "Below are the steps \n ${viewModel.bugSteps}"

        return Pair(subject, message)
    }
}