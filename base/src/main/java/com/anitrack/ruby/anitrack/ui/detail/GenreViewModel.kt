package com.anitrack.ruby.anitrack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anitrack.ruby.anitrack.data.GenreRepository
import com.anitrack.ruby.anitrack.model.GenresResult
import com.anitrack.ruby.anitrack.network.models.genre.Genre


class GenreViewModel(repository: GenreRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val genreResult: LiveData<GenresResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val result: LiveData<List<Genre>> = Transformations.switchMap(genreResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(genreResult,
            { it -> it.networkErrors })

    fun search(id: String) {
        if (queryLiveData.value == id) return
        queryLiveData.postValue(id)
    }

}
