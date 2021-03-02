package com.th3pl4gu3.mes.ui.about

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.mes.databinding.FragmentAboutBinding
import com.th3pl4gu3.mes.ui.utils.extensions.navigateTo
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

        subscribeLists()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onItemClick(item: AboutItem.Item) {

        when (item) {

            AboutItem.Item.DEV_REPORT_BUG -> navigateTo(AboutFragmentDirections.actionFragmentAboutToFragmentBugReport())

            AboutItem.Item.DEV_SUGGESTION -> navigateTo(AboutFragmentDirections.actionFragmentAboutToFragmentServiceSuggestion())

            else -> Log.v("ABOUT_US_CLICKS", "${item.name} has been clicked")
        }
    }

    // Private functions
    private fun subscribeLists() {
        binding.includeAboutDevelopment.RecyclerViewDevelopment.apply {
            with(AboutItemAdapter(this@AboutFragment)) {
                this@apply.adapter = this@with
                setHasFixedSize(true)
                this@with.submitList(viewModel.getDevelopmentList())
            }
        }

        binding.includeAboutOthers.RecyclerViewOther.apply {
            with(AboutItemAdapter(this@AboutFragment)) {
                this@apply.adapter = this@with
                setHasFixedSize(true)
                this@with.submitList(viewModel.getOtherList())
            }
        }
    }
//
//    private fun subscribeDevelopmentList() {
//        val adapter = AboutItemAdapter(this)
//
//        binding.includeAboutDevelopment.RecyclerViewDevelopment.apply {
//            this.adapter = adapter
//            setHasFixedSize(true)
//        }
//
//        adapter.submitList(viewModel.getDevelopmentList())
//    }
//
//    private fun subscribeOthersList() {
//        val adapter = AboutItemAdapter(this)
//
//        binding.includeAboutOthers.RecyclerViewOther.apply {
//            this.adapter = adapter
//            setHasFixedSize(true)
//        }
//
//        adapter.submitList(viewModel.getOtherList())
//    }
}