package com.th3pl4gu3.mes.ui.bug_report

import android.app.Application
import androidx.databinding.Bindable
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel

class BugReportViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private var mBugIdentified: String = ""
    private var mBugSteps: String = ""


    // Bind-able two-way binding
    var bugIdentified: String
        @Bindable get() {
            return mBugIdentified
        }
        set(value) {
            mBugIdentified = value
            notifyPropertyChanged(BR.bugIdentified)
        }

    var bugSteps: String
        @Bindable get() {
            return mBugSteps
        }
        set(value) {
            mBugSteps = value
            notifyPropertyChanged(BR.bugSteps)
        }
}