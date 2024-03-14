package com.pmm.api

import retrofit2.Call
import retrofit2.http.GET

interface DogAPI {
    @GET("breeds/image/random")
    fun getRandomDogImage(): Call<ImageResponse>
    @GET("breeds/image/random")
    fun getNewDogImage(): Call<ImageResponse>

}

data class ImageResponse(val message: String, val status: String)
