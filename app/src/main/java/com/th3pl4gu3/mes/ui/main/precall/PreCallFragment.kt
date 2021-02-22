package com.th3pl4gu3.mes.ui.main.precall

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.th3pl4gu3.mes.databinding.FragmentPrecallBinding
import com.th3pl4gu3.mes.ui.utils.extensions.pop
import com.th3pl4gu3.mes.ui.utils.extensions.toPhoneCallIntent
import java.util.concurrent.TimeUnit


class PreCallFragment : Fragment() {

    private var mBinding: FragmentPrecallBinding? = null
    private var mViewModel: PreCallViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPrecallBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PreCallViewModel::class.java)
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumberRetrieved = PreCallFragmentArgs.fromBundle(requireArguments()).phonenumber

        Log.d("PHONE_NUMBER_TEST", "Phone number retrieved: $phoneNumberRetrieved")

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(
                    "PHONE_NUMBER_TEST",
                    "Ticks: ${TimeUnit.SECONDS.convert(millisUntilFinished, TimeUnit.MILLISECONDS)}"
                )
            }

            override fun onFinish() {
                //startActivity("8962356413531".toPhoneCallIntent)
                Log.d("PHONE_NUMBER_TEST", "Call Started")
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()

        /*
        * If the countdown is successful or
        * if the user leaves the screen prematurely
        * fragment will be popped out of back stack
        */
        pop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}