package com.th3pl4gu3.mes.ui.main.all_services

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.utils.DoubleTrigger
import com.th3pl4gu3.mes.ui.utils.Global
import com.th3pl4gu3.mes.ui.utils.extensions.getString
import com.th3pl4gu3.mes.ui.utils.extensions.lowercase
import kotlinx.coroutines.launch
import java.lang.Exception

class AllServicesViewModel(application: Application) : AndroidViewModel(application) {

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

    init {
        refreshServices()
    }

    // Functions
    internal fun refreshServices() {

        viewModelScope.launch {

            // Set loading to true to
            // notify the fragment that loading
            // has started and to show loading animation
            // TODO("Show loading animation only when loading from cache. Background refresh should not block UI")
            mLoading.value = true
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