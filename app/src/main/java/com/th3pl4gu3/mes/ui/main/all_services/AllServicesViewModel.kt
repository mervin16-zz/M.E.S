package com.th3pl4gu3.mes.ui.main.all_services

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.api.ApiRepository
import com.th3pl4gu3.mes.api.Service
import kotlinx.coroutines.launch

class AllServicesViewModel(application: Application) : AndroidViewModel(application) {

    // Private Variables
    private val mServices = MutableLiveData<List<Service>>()
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(true)

    // Properties
    val services: LiveData<List<Service>>
        get() = mServices

    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    init {
        loadServices()
    }

    // Functions
    internal fun loadServices() {

        // Set loading to true to
        // notify the fragment that loading
        // has started and to show loading animation
        mLoading.value = true

        viewModelScope.launch {
            val response = ApiRepository.getInstance().getServices()

            if (response.success) {
                mServices.value = response.services
            } else {
                mMessage.value = response.message
            }
        }.invokeOnCompletion {
            // Set loading to false to
            // notify the fragment that loading
            // has completed and to hide loading animation
            mLoading.value = false
        }
    }
}