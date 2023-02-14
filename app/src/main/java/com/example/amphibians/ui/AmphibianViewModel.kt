/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

data class AmphibiansState(
    val amphibians: List<Amphibian>,
    val status: AmphibianApiStatus = AmphibianApiStatus.LOADING
)

data class AmphibianDetailState(
    val amphibians: Amphibian,
    val status: AmphibianApiStatus = AmphibianApiStatus.LOADING
)

class AmphibianViewModel : ViewModel() {
    private val _amphibians = MutableLiveData<AmphibiansState>()
    val amphibians: LiveData<AmphibiansState>
        get() = _amphibians

    private val _amphibian = MutableLiveData<AmphibianDetailState>()
    val amphibian: LiveData<AmphibianDetailState>
        get() = _amphibian


//    // TODO: Create properties to represent MutableLiveData and LiveData for the API status
//    private val _status = MutableLiveData<AmphibianApiStatus>()
//    val status: LiveData<AmphibianApiStatus>
//        get() = _status
//
//    // TODO: Create properties to represent MutableLiveData and LiveData for a list of amphibian objects
//    private val _amphibians = MutableLiveData<List<Amphibian>>()
//    val amphibians: LiveData<List<Amphibian>>
//        get() = _amphibians
//
//    // TODO: Create properties to represent MutableLiveData and LiveData for a single amphibian object.
//    //  This will be used to display the details of an amphibian when a list item is clicked
//    private val _amphibian = MutableLiveData<Amphibian>()
//    val amphibian: LiveData<Amphibian>
//        get() = _amphibian


    // TODO: Create a function that gets a list of amphibians fro`m the api service and sets the
    //  status via a Coroutine
    private fun getAmphibians(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _amphibians.postValue(AmphibiansState(AmphibianApi.retrofitService.getAmphibians(), AmphibianApiStatus.DONE))
            }
            catch (e: IOException) {
                _amphibians.postValue(AmphibiansState(listOf(), AmphibianApiStatus.ERROR))
            }
        }
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
        // TODO: Set the amphibian object
        _amphibian.value = AmphibianDetailState(amphibian, AmphibianApiStatus.DONE)

    }
    init {
        getAmphibians()
    }
}
