package com.anitrack.ruby.anitrack.data.source

import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient

open class GeneralRepository(private val service: RetrofitClient) {

    // LiveData of network errors.
    protected val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    protected var isRequestInProgress = false
}