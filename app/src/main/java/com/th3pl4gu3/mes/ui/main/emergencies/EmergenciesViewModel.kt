package com.th3pl4gu3.mes.ui.main.emergencies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.api.ApiRepository
import com.th3pl4gu3.mes.api.Service
import kotlinx.coroutines.launch

class EmergenciesViewModel(application: Application) : AndroidViewModel(application) {

    // Private Variables
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(true)
    private var mEmergencies = MutableLiveData<List<Service>>()
    private var mEmergencyButtonHolder: Service? = null

    // Properties
    val emergencyButtonHolder: Service?
        get() = mEmergencyButtonHolder

    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    val emergencies: LiveData<List<Service>>
        get() = mEmergencies

    init {
        loadServices()
    }

    internal fun loadServices() {

        // Set loading to true to
        // notify the fragment that loading
        // has started and to show loading animation
        mLoading.value = true
        // Reset previous Message value
        mMessage.value = null

        viewModelScope.launch {
            //TODO("Ensure connected to internet first")
            val response = ApiRepository.getInstance().getEmergencies()

            if (response.success) {
                // Get emergency police
                mEmergencyButtonHolder = response.services.first {
                    it.identifier == "security-police-direct-1"
                }

                // Bind emergencies
                mEmergencies.value = ArrayList(response.services).apply {
                    this.removeIf { it.identifier == "security-police-direct-1" }
                }

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