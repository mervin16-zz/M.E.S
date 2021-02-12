package com.th3pl4gu3.mes.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentAboutBinding
import com.th3pl4gu3.mes.ui.utils.listeners.AboutItemListener

class AboutFragment : Fragment(), AboutItemListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeDevelopmentList()

        subscribeOthersList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onItemClick(item: AboutItem.Item) {
        //TODO("Implement About Item Clicks")
    }

    // Private functions
    private fun subscribeDevelopmentList() {
        val adapter = AboutItemAdapter(this)

        binding.includeAboutDevelopment.RecyclerViewDevelopment.apply {
            this.adapter = adapter
            setHasFixedSize(true)
        }

        adapter.submitList(viewModel.getDevelopmentList())
    }

    private fun subscribeOthersList() {
        val adapter = AboutItemAdapter(this)

        binding.includeAboutOthers.RecyclerViewOther.apply {
            this.adapter = adapter
            setHasFixedSize(true)
        }

        adapter.submitList(viewModel.getOtherList())
    }
}