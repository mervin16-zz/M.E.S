package com.th3pl4gu3.mes.ui.main.all_services

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.ui.main.ServiceLoader
import com.th3pl4gu3.mes.ui.utils.extensions.getString
import com.th3pl4gu3.mes.ui.utils.extensions.lowercase
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.collections.ArrayList

class AllServicesViewModel(application: Application) : AndroidViewModel(application) {

    // Private Variables
    private val mServices = MutableLiveData<List<Service>>()
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(true)
    private var mSearchQuery = MutableLiveData<String>()
    private var mRawServices = ArrayList<Service>()

    // Properties
    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    val services: LiveData<List<Service>> = Transformations.switchMap(mSearchQuery) { query ->
        if (query.isEmpty()) {
            mServices.value = mRawServices
        } else {
            mServices.value = mRawServices.filter {
                it.name.lowercase().contains(query.lowercase()) ||
                        it.identifier.lowercase().contains(query.lowercase()) ||
                        it.type.lowercase().contains(query.lowercase())
            }
        }

        mServices
    }

    init {
        loadServices()
    }

    // Functions
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
                    val services = fetch(emergencies = false)

                    if (services != null) {
                        // Bind raw services
                        mRawServices = ArrayList(services)

                        // Set the default search string
                        mSearchQuery.value = ""
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

    internal fun search(query: String) {
        mSearchQuery.value = query
    }
}