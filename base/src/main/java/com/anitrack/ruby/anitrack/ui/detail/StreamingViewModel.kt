package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.remote.StreamingResult
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.Streaming


class StreamingViewModel(streamingRepository: StreamingRepository) : ViewModel() {

    private val streamingQueryLiveData = MutableLiveData<String>()
    private val streamingResult: LiveData<StreamingResult> = Transformations.map(streamingQueryLiveData, {
        streamingRepository.search(it)
    })

    val streamingDataResult: LiveData<List<Streaming>> = Transformations.switchMap(streamingResult,
            { it -> it.data })
    val streamingNetworkErrors: LiveData<String> = Transformations.switchMap(streamingResult,
            { it -> it.networkErrors })

    fun search(id: String) {
        if (streamingQueryLiveData.value == id) return
        streamingQueryLiveData.postValue(id)
    }

}
