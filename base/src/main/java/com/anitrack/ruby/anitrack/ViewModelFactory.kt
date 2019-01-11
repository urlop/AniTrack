/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anitrack.ruby.anitrack

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anitrack.ruby.anitrack.data.source.AnimeRepository
import com.anitrack.ruby.anitrack.data.source.GenreRepository
import com.anitrack.ruby.anitrack.data.source.StreamingRepository
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient
import com.anitrack.ruby.anitrack.ui.detail.GenreViewModel
import com.anitrack.ruby.anitrack.ui.detail.StreamingViewModel
import com.anitrack.ruby.anitrack.ui.main.MainViewModel

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 *
 * Source: https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live-kotlin
 */
class ViewModelFactory (
        private val context: Context,
        private val retrofitClient: RetrofitClient
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(AnimeRepository(retrofitClient))
                    isAssignableFrom(GenreViewModel::class.java) ->
                        GenreViewModel(GenreRepository(retrofitClient))
                    isAssignableFrom(StreamingViewModel::class.java) ->
                        StreamingViewModel(StreamingRepository(retrofitClient))
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context, retrofitClient: RetrofitClient) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(context, retrofitClient)
                            .also { INSTANCE = it }
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
