package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.*
import com.anitrack.ruby.anitrack.data.source.GenreRepository
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.local.models.Anime
import com.anitrack.ruby.anitrack.data.source.remote.GenresResult
import com.anitrack.ruby.anitrack.data.source.remote.StreamingResult
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.Genre
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.Streaming
import com.anitrack.ruby.anitrack.toAnime
import com.anitrack.ruby.anitrack.toGenreList
import com.anitrack.ruby.anitrack.toStreamingList

class AnimeDetailViewModel(val genreRepository: GenreRepository, val streamingRepository: StreamingRepository) : ViewModel() {

    val animeMediatorLiveData = MutableLiveData<Anime>()

    //Genre
    private val genreQueryLiveData = MutableLiveData<String>()
    private var genreResult: LiveData<GenresResult>? = null

    var genreFinalResult: LiveData<List<Genre>>? = null
    var genreNetworkErrors: LiveData<String>? = null

    //Streaming
    private val streamingQueryLiveData = MutableLiveData<String>()
    private var streamingResult: LiveData<StreamingResult>? = null

    var streamingFinalResult: LiveData<List<Streaming>>? = null
    var streamingNetworkErrors: LiveData<String>? = null

    var liveDataMerger : LiveData<Anime>? = null

    fun initialize(anime: Anime) {
        genreResult = Transformations.map(genreQueryLiveData, {
            genreRepository.search(it)
        })
        genreFinalResult = Transformations.switchMap(genreResult!!,
        { it -> it.data })
        genreNetworkErrors = Transformations.switchMap(genreResult!!,
        { it -> it.networkErrors })

        streamingResult = Transformations.map(streamingQueryLiveData, {
            streamingRepository.search(it)
        })
        streamingFinalResult = Transformations.switchMap(streamingResult!!,
        { it -> it.data })
        streamingNetworkErrors = Transformations.switchMap(streamingResult!!,
        { it -> it.networkErrors })

        animeMediatorLiveData.postValue(anime)

        liveDataMerger = zipLiveData(genreFinalResult!!, streamingFinalResult!!)
    }

    fun initialize(anime: DataAnime) {
        initialize(toAnime(anime, anime.attributes))
    }

    fun search(id: String) {
        searchGenres(id)
        searchStreamings(id)
    }

    fun searchGenres(id: String) {
        if (genreQueryLiveData.value == id) return
        genreQueryLiveData.postValue(id)
    }

    fun searchStreamings(id: String) {
        if (streamingQueryLiveData.value == id) return
        streamingQueryLiveData.postValue(id)
    }

    fun zipLiveData(a: LiveData<List<Genre>>, b: LiveData<List<Streaming>>): LiveData<Anime> {
        return MediatorLiveData<Anime>().apply {
            var lastA: List<Genre>? = null
            var lastB: List<Streaming>? = null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                if (localLastA != null && localLastB != null) {
                    this.value = animeMediatorLiveData.value
                    this.value!!.genres = toGenreList(localLastA) //TODO Create a singleton of the viewmodel per each anime
                    this.value!!.streamingLinks = toStreamingList(localLastB)
                    this.postValue(this.value)
                }
            }

            addSource(a) {
                lastA = it
                update()
            }
            addSource(b) {
                lastB = it
                update()
            }
        }
    }
}