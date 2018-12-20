package com.anitrack.ruby.anitrack.data

import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.network.RetrofitClient

open class GeneralRepository(private val service: RetrofitClient) {

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
}