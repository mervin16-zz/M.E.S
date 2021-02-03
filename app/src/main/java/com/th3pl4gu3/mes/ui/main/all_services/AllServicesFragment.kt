package com.th3pl4gu3.mes.ui.main.all_services

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.databinding.FragmentAllServicesBinding
import kotlinx.coroutines.launch

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeServices()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    // Private Functions
    private fun subscribeServices() {
        val adapter = ServiceAdapter()

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
}