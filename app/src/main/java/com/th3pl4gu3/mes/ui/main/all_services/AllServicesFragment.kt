package com.th3pl4gu3.mes.ui.main.all_services

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.FragmentAllServicesBinding
import com.th3pl4gu3.mes.ui.utils.extensions.action
import com.th3pl4gu3.mes.ui.utils.extensions.navigateTo
import com.th3pl4gu3.mes.ui.utils.extensions.snackInf
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener
import kotlinx.coroutines.launch

class AllServicesFragment : Fragment(), PhoneNumberListener {

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeServices()

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onPhoneNumberClicked(number: Long) {
        Log.v("NUMBER_CLICK_TEST", number.toString())
        // TODO("Implement phone intent")
    }

    // Private Functions
    private fun subscribeServices() {
        val adapter = ServiceAdapter(this)

        binding.RecyclerViewServices.apply {
            /*
            * State that layout size will not change for better performance
            */
            setHasFixedSize(true)

            /* Bind the layout manager */
            layoutManager = LinearLayoutManager(requireContext())

            /* Bind the adapter to the RecyclerView*/
            this.adapter = adapter
        }

        viewModel.services.observe(viewLifecycleOwner, { services ->
            if (services != null) {
                lifecycleScope.launch {
                    adapter.submitList(services)

                    // Notify a change in list
                    binding.RecyclerViewServices.adapter = adapter
                }
            }
        })

    }

    private fun subscribeObservers() {
        viewModel.message.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.RootAllServices.snackInf(it) {
                    action(getString(R.string.action_retry)) {
                        viewModel.loadServices()
                    }
                }
            }
        })

        binding.TextFieldSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.search(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                return
            }
        })
    }
}