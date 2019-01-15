package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.*
import com.anitrack.ruby.anitrack.data.source.GenreRepository
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.animeToDb
import com.anitrack.ruby.anitrack.data.source.genresListToDb
import com.anitrack.ruby.anitrack.data.source.local.models.Anime
import com.anitrack.ruby.anitrack.data.source.remote.GenresResult
import com.anitrack.ruby.anitrack.data.source.remote.StreamingResult
import com.anitrack.ruby.anitrack.data.source.remote.models.AnimeWS
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.GenreWS
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.StreamingWS
import androidx.lifecycle.MediatorLiveData



class AnimeDetailViewModel(genreRepository: GenreRepository, streamingRepository: StreamingRepository) : ViewModel() {

    private val animeQueryData = MutableLiveData<String>()
    /*private val animeResult: LiveData<AnimeResult> = Transformations.map(animeQueryData, {
        genreRepository.search(animeQueryData)
    })*/

    //val animeDataResult: LiveData<Anime>? = Transformations.switchMap(animeResult,
    //        { it -> it.data })
    var animeMediatorLiveData: MediatorLiveData<Anime> = MediatorLiveData()

    /*animeMediatorLiveData.addSource(genreDataResult, {
        this.value?.genres = genresListToDb(it)
    }*/

        /*this.addSource(streamingDataResult) {
            this.value?.streamingLinks = null //TODO
        }*/

    fun <A, B> MediatorLiveData<Any>.zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
        return MediatorLiveData<Pair<A, B>>().apply {
            var lastA: A? = null
            var lastB: B? = null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                if (localLastA != null && localLastB != null)
                    this.value = Pair(localLastA, localLastB)
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

    //GenreWS
    //private val genreQueryLiveData = MutableLiveData<String>()
    private val genreResult: LiveData<GenresResult> = Transformations.map(animeQueryData, {
        genreRepository.search(it)
    })

    val genreDataResult: LiveData<List<GenreWS>> = Transformations.switchMap(genreResult,
            { it -> it.data })
    val genreNetworkErrors: LiveData<String> = Transformations.switchMap(genreResult,
            { it -> it.networkErrors })

    //StreamingWS
    //private val streamingQueryLiveData = MutableLiveData<String>()
    private val streamingResult: LiveData<StreamingResult> = Transformations.map(animeQueryData, {
        streamingRepository.search(it)
    })

    val streamingDataResult: LiveData<List<StreamingWS>> = Transformations.switchMap(streamingResult,
            { it -> it.data })
    val streamingNetworkErrors: LiveData<String> = Transformations.switchMap(streamingResult,
            { it -> it.networkErrors })

    init {
        animeMediatorLiveData.apply {
            addSource(genreDataResult) {
                //animeMediatorLiveData.value?.genres = emptyList()
                animeMediatorLiveData.value?.genres = genresListToDb(it)
                animeMediatorLiveData.postValue(animeMediatorLiveData.value)
            }
        }
    }


    fun initialize(anime: Anime) {
        animeMediatorLiveData.postValue(anime)
    }

    fun initialize(anime: AnimeWS) {
        val result = animeToDb(anime)
        result.genres = emptyList()
        animeMediatorLiveData.postValue(result)
    }

    fun search() {
        //if (animeQueryData.value == id) return
        animeQueryData.postValue(animeMediatorLiveData.value?.id ?: "0")
        //searchGenres(id)
        //searchStreamings(id)
    }

    /*fun searchGenres(id: String) {
        if (genreQueryLiveData.value == id) return
        genreQueryLiveData.postValue(id)
    }

    fun searchStreamings(id: String) {
        if (streamingQueryLiveData.value == id) return
        streamingQueryLiveData.postValue(id)
    }*/

}