package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.remote.StreamingResult
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.Streaming


class StreamingViewModel(repository: StreamingRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val streamingResult: LiveData<StreamingResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val result: LiveData<List<Streaming>> = Transformations.switchMap(streamingResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(streamingResult,
            { it -> it.networkErrors })

    fun search(id: String) {
        if (queryLiveData.value == id) return
        queryLiveData.postValue(id)
    }

}
