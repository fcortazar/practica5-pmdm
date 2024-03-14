package com.pmm.api

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchDogImage()
    }

    private fun fetchDogImage() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/") // Asegúrate de reemplazar esto con la URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dogAPI = retrofit.create(DogAPI::class.java)

        dogAPI.getRandomDogImage().enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val imageUrl = response.body()?.message
                if (imageUrl != null) {
                    val imageView: ImageView = findViewById(R.id.imageView)
                    Picasso.get().load(imageUrl).into(imageView)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Handle the error
            }
        })
    }

    private fun fetchNewDogImage(imageView: ImageView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dogAPI = retrofit.create(DogAPI::class.java)

        dogAPI.getNewDogImage().enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val imageUrl = response.body()?.message
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageView)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Handle the error
            }
        })
    }
}