package com.anitrack.ruby.anitrack.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anitrack.ruby.anitrack.data.source.AnimeRepository
import com.anitrack.ruby.anitrack.data.source.remote.AnimeSearchResult
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime


class MainViewModel(val repository: AnimeRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val animeResult: LiveData<AnimeSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val result: LiveData<List<DataAnime>> = Transformations.switchMap(animeResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(animeResult,
            { it -> it.networkErrors })

    init {
        searchAnime(AnimeRepository.SORT_POPULARITY, false, true);
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchAnime(sort: String, reverseSort: Boolean, reset: Boolean) {
        var finalSortQuery = sort
        if (reverseSort) finalSortQuery = reverseSortString(finalSortQuery)
        if (reset.not() && queryLiveData.value == finalSortQuery) return // FIXME refreshing on rotate
        queryLiveData.postValue(finalSortQuery)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

    fun reverseSortString(sort: String): String = if (sort[0] == '-') sort.drop(1) else "-$sort"

}
