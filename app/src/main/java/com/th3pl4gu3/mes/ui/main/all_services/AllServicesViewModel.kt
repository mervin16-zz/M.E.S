package com.th3pl4gu3.mes.ui.main.all_services

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.utils.helpers.Global
import com.th3pl4gu3.mes.ui.utils.extensions.requireStringRes
import com.th3pl4gu3.mes.ui.utils.extensions.lowercase
import com.th3pl4gu3.mes.ui.utils.helpers.Global.SIZE_PAGE_LIST_DEFAULT
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class AllServicesViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(false)
    private var mSearchQuery = MutableLiveData("")
    private var mServices = ServiceRepository.getInstance(getApplication()).getAll()

    // Properties
    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    val services = Transformations.switchMap(mSearchQuery) { query ->
        return@switchMap mServices.filter(query).toLiveData(SIZE_PAGE_LIST_DEFAULT)
    }

    private fun DataSource.Factory<Int, Service>.filter(query: String): DataSource.Factory<Int, Service> {
        return this.mapByPage { services ->
            if (query.isEmpty()) {
                return@mapByPage services
            } else {
                return@mapByPage services.filter {
                    it.name.lowercase().contains(query.lowercase()) ||
                            it.identifier.lowercase().contains(query.lowercase()) ||
                            it.type.lowercase().contains(query.lowercase())
                }
            }
        }
    }

    // Bind-able two-way binding
    var searchQuery: String
        @Bindable get() {
            return mSearchQuery.value!!
        }
        set(value) {
            mSearchQuery.value = value
            notifyPropertyChanged(BR.searchQuery)
        }

    init {
        startLoading()

        refreshServices()
    }

    // Functions
    internal fun refreshServices() {

        viewModelScope.launch {

            // Reset previous Message value
            mMessage.value = null
            val serviceRepo = ServiceRepository.getInstance(getApplication())
            val hasCache = serviceRepo.hasCache()

            try {
                if (Global.isNetworkConnected) {
                    with(serviceRepo.refresh()) {
                        if (!hasCache) {
                            mMessage.value = this
                        }
                    }
                } else {
                    // If not connected to internet, we check if cache present already
                    // If not, display error message
                    if (!hasCache) {
                        mMessage.value = requireStringRes(R.string.message_info_no_internet)
                    }
                }

            } catch (e: Exception) {
                mMessage.value = requireStringRes(R.string.message_error_bug_report)
            }

        }
    }

    internal fun stopLoading() {

        // Set loading to false to
        // notify the fragment that loading
        // has completed and to hide loading animation
        mLoading.value = false

    }

    private fun startLoading() {

        // Set loading to true to
        // notify the fragment that loading
        // has started and to show loading animation
        mLoading.value = true

    }
}