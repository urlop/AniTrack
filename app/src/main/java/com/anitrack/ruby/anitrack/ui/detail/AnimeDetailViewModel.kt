package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.*
import com.anitrack.ruby.anitrack.data.source.GenreRepository
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.local.models.Anime
import com.anitrack.ruby.anitrack.data.source.remote.AnimeResult
import com.anitrack.ruby.anitrack.data.source.remote.GenresResult
import com.anitrack.ruby.anitrack.data.source.remote.StreamingResult
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.Genre
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.Streaming
import com.anitrack.ruby.anitrack.toAnime

class AnimeDetailViewModel(genreRepository: GenreRepository, streamingRepository: StreamingRepository) : ViewModel() {

    /*private val animeQueryData = MutableLiveData<String>()*/
    /*private val animeResult: LiveData<Anime> =  Transformations.map(animeQueryData, {
        search(id)
    })*/
    /*val animeDataResult: LiveData<Anime>? = Transformations.switchMap(animeResult,
            { it -> it })*/
    /*val animeMediatorLiveData = MediatorLiveData<Anime>().apply {
        this.addSource(animeResult) {
            this.value = it
        }
    }*/
    val animeMediatorLiveData = MutableLiveData<Anime>()

    //Genre
    private val genreQueryLiveData = MutableLiveData<String>()
    private val genreResult: LiveData<GenresResult> = Transformations.map(genreQueryLiveData, {
        genreRepository.search(it)
    })

    val genreDataResult: LiveData<List<Genre>> = Transformations.switchMap(genreResult,
            { it -> it.data })
    val genreNetworkErrors: LiveData<String> = Transformations.switchMap(genreResult,
            { it -> it.networkErrors })

    //Streaming
    private val streamingQueryLiveData = MutableLiveData<String>()
    private val streamingResult: LiveData<StreamingResult> = Transformations.map(streamingQueryLiveData, {
        streamingRepository.search(it)
    })

    val streamingDataResult: LiveData<List<Streaming>> = Transformations.switchMap(streamingResult,
            { it -> it.data })
    val streamingNetworkErrors: LiveData<String> = Transformations.switchMap(streamingResult,
            { it -> it.networkErrors })

    fun initialize(anime: Anime){
        animeMediatorLiveData.postValue(anime)
    }

    fun initialize(anime: DataAnime){
        animeMediatorLiveData.postValue(toAnime(anime, anime.attributes)) //anime)
    }

    fun search(id: String){
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

}