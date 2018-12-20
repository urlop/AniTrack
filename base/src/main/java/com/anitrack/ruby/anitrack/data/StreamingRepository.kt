package com.anitrack.ruby.anitrack.data

import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.model.StreamingResult
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.streaming.BaseStreaming
import com.anitrack.ruby.anitrack.network.models.streaming.Streaming
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class that works with local and remote data sources.
 */
class StreamingRepository(private val service: RetrofitClient) : GeneralRepository(service) {

    var streamingList: MutableLiveData<List<Streaming>> = MutableLiveData()

    fun search(id: String): StreamingResult {
        requeststreamingList(id)

        return StreamingResult(streamingList, networkErrors)
    }

    private fun requeststreamingList(id: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        service.getStreaming(id)
                .enqueue(object : Callback<BaseStreaming> {
                    override fun onResponse(call: Call<BaseStreaming>, response: Response<BaseStreaming>) {
                        try {
                            if (streamingList.value != null && streamingList.value!!.isNotEmpty()) {
                                streamingList.postValue((streamingList.value!!.toTypedArray() + response.body()!!.data!!.toTypedArray()).toList())
                            } else {
                                streamingList.postValue(response.body()!!.data)
                            }
                            isRequestInProgress = false
                        } catch (e: Exception) {
                            networkErrors.postValue("Streamings not available") //404 {"error":"We couldn't find the record you were looking for."}
                            isRequestInProgress = false
                        }
                    }

                    override fun onFailure(call: Call<BaseStreaming>, t: Throwable) {
                        networkErrors.postValue(t.message)
                        isRequestInProgress = false
                    }
                })
    }

}