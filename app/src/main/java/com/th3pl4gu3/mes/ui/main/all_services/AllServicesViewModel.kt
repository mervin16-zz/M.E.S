package com.th3pl4gu3.mes.ui.main.all_services

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.utils.helpers.DoubleTrigger
import com.th3pl4gu3.mes.ui.utils.helpers.Global
import com.th3pl4gu3.mes.ui.utils.extensions.getString
import com.th3pl4gu3.mes.ui.utils.extensions.lowercase
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class AllServicesViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(false)
    private var mSearchQuery = MutableLiveData("")

    // Properties
    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    val services = Transformations.map(
        DoubleTrigger(
            ServiceRepository.getInstance(getApplication()).getAll(),
            mSearchQuery
        )
    ) { pair ->

        val services = pair.first
        val query = pair.second

        if (query.isNullOrEmpty()) {
            return@map services
        } else {
            return@map services?.filter {
                it.name.lowercase().contains(query.lowercase()) ||
                        it.identifier.lowercase().contains(query.lowercase()) ||
                        it.type.lowercase().contains(query.lowercase())
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

            try {
                if (Global.isNetworkConnected) {
                    ServiceRepository.getInstance(getApplication()).refresh()
                } else {
                    mMessage.value = getString(R.string.message_info_no_internet)
                }

            } catch (e: Exception) {
                mMessage.value = getString(R.string.message_error_bug_report)
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