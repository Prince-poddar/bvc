package com.example.bvc.networking

import android.util.Log
import com.example.bvc.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {


    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apikey: String,
        @Query("s") s: String,
        @Query("type") type: String,
    ): Response<MoviesResponse>


}