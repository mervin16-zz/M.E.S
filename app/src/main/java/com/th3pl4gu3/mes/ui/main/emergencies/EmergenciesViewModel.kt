package com.th3pl4gu3.mes.ui.main.emergencies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.ui.main.ServiceLoader
import com.th3pl4gu3.mes.ui.utils.Global.ID_API_SERVICE_POLICE
import com.th3pl4gu3.mes.ui.utils.extensions.getString
import kotlinx.coroutines.launch
import java.lang.Exception

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

            try {
                with(ServiceLoader.getInstance(getApplication())) {
                    val services = fetch(emergencies = true)

                    if (services != null) {
                        // Get emergency police
                        mEmergencyButtonHolder = services.first {
                            it.identifier == ID_API_SERVICE_POLICE
                        }

                        // Bind emergencies
                        mEmergencies.value = ArrayList(services).apply {
                            this.removeIf { it.identifier == ID_API_SERVICE_POLICE }
                        }
                    } else {
                        mMessage.value = this.message
                    }
                }
            } catch (e: Exception) {
                mMessage.value = getString(R.string.message_error_bug_report)
            }

        }.invokeOnCompletion {
            // Set loading to false to
            // notify the fragment that loading
            // has completed and to hide loading animation
            mLoading.value = false
        }
    }
}