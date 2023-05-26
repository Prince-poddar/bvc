package com.example.bvc.networking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bvc.response.MoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _moviesResponse = MutableLiveData<Resource<MoviesResponse>>()
    val moviesResponse: MutableLiveData<Resource<MoviesResponse>>
        get() = _moviesResponse


    fun getMoviesListFromApi(apiKey: String, text: String, type: String) = viewModelScope.launch {
        _moviesResponse.postValue(Resource.loading(null))
        kotlin.runCatching {
            withContext(Dispatchers.IO){
                mainRepository.hitMoviesApi(apiKey,text,type)
            }
        }.onSuccess {
            if (it.isSuccessful) {
                _moviesResponse.postValue(Resource.success(it.body()))
            } else {
                _moviesResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }

        }.onFailure {
            _moviesResponse.postValue(Resource.error(it.message.toString(), null))

        }
    }

}
