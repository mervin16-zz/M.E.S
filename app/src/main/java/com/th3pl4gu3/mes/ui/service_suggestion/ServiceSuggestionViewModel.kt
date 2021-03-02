package com.th3pl4gu3.mes.ui.service_suggestion

import android.app.Application
import androidx.databinding.Bindable
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel

class ServiceSuggestionViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private var mServiceName: String = ""
    private var mServiceNumber: String = ""
    private var mServiceProof: String = ""


    // Bind-able two-way binding
    var serviceName: String
        @Bindable get() {
            return mServiceName
        }
        set(value) {
            mServiceName = value
            notifyPropertyChanged(BR.serviceName)
        }

    var serviceNumber: String
        @Bindable get() {
            return mServiceNumber
        }
        set(value) {
            mServiceNumber = value
            notifyPropertyChanged(BR.serviceNumber)
        }

    var serviceProof: String
        @Bindable get() {
            return mServiceProof
        }
        set(value) {
            mServiceProof = value
            notifyPropertyChanged(BR.serviceProof)
        }
}