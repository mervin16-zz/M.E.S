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

    // Properties
    val services: LiveData<List<Service>>
        get() = mServices

    init {
        loadServices()
    }

    // Functions
    private fun loadServices() {
        viewModelScope.launch {
            val response = ApiRepository.getInstance().getServices()

            if (response.success) {
                mServices.value = response.services
            } else {
                // Do Something
            }

        }
    }
}