package com.example.bvc.networking

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiServices
) {
    suspend fun hitMoviesApi(apiKey: String, text: String, type: String) = apiService
        .getMovies(apiKey,text,type)

}