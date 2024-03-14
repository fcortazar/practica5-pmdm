package com.pmm.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogBreedAdapter(private var dogBreeds: MutableList<DogBreed>) : RecyclerView.Adapter<DogBreedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val likeButton: Button = itemView.findViewById(R.id.likeButton)
        val dislikeButton: Button = itemView.findViewById(R.id.dislikeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_breed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dogBreed = dogBreeds[position]
        Picasso.get().load(dogBreed.imageUrl).into(holder.imageView)

        holder.likeButton.setOnClickListener {
            fetchNewDogImage(position)
        }

        holder.dislikeButton.setOnClickListener {
            fetchNewDogImage(position)
        }
    }

    private fun fetchNewDogImage(position: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dogAPI = retrofit.create(DogAPI::class.java)

        dogAPI.getNewDogImage().enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val imageUrl = response.body()?.message
                if (imageUrl != null) {
                    dogBreeds[position].imageUrl = imageUrl
                    notifyItemChanged(position)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Handle the error
            }
        })
    }

    override fun getItemCount() = dogBreeds.size
}