package com.th3pl4gu3.mes.ui.main.emergencies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.databinding.FragmentEmergenciesBinding
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener
import kotlinx.coroutines.launch

class EmergenciesFragment : Fragment(), PhoneNumberListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeButtons()

        subscribeEmergencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onPhoneNumberClicked(number: Long) {
        Log.v("NUMBER_CLICK_TEST", number.toString())
        // TODO("Implement phone intent")
    }


    private fun subscribeButtons() {
        // Emergency Button
        binding.EmergencyButton.setOnLongClickListener {
            Log.v("NUMBER_CLICK_TEST", "999")
            true
            // TODO("Implement phone intent")
        }
    }

    private fun subscribeEmergencies() {
        // TODO("Replace fake list with real list)
        val list = ArrayList<Service>().apply {
            add(
                Service(
                    "1",
                    "Hospital",
                    "HEALTH",
                    "https://img.icons8.com/fluent/100/000000/policeman-male.png",
                    111
                )
            )
            add(
                Service(
                    "2",
                    "Emergency Police",
                    "SECURITY",
                    "https://img.icons8.com/fluent/100/000000/policeman-male.png",
                    222
                )
            )
            add(
                Service(
                    "3",
                    "SAMU",
                    "HEALTH",
                    "https://img.icons8.com/fluent/100/000000/policeman-male.png",
                    333
                )
            )
            add(
                Service(
                    "4",
                    "Fire Extinguishers",
                    "SECURITY",
                    "https://img.icons8.com/fluent/100/000000/policeman-male.png",
                    444
                )
            )
        }

        val adapter = EmergencyAdapter(this)

        binding.RecyclerViewEmergencies.apply {
            /*
            * State that layout size will not change for better performance
            */
            setHasFixedSize(true)

            /* Bind the layout manager */
            binding.RecyclerViewEmergencies.layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

            /* Bind the adapter to the RecyclerView*/
            this.adapter = adapter
        }

        adapter.submitList(list)
    }
}