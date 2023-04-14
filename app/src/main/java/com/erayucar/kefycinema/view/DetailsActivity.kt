package com.erayucar.kefycinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.ActivityDetailsBinding
import com.erayucar.kefycinema.model.MovieModel

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var movieModel: MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadIntoLayout()




    }

        fun loadIntoLayout(){
        movieModel = intent.extras!!.getParcelable("movie")!!
        binding.textOriginalTitle.text = movieModel.original_title
        binding.language.text = movieModel.original_language
        binding.movieOverview.text = movieModel.overview
        binding.releaseDate.text = movieModel.release_date
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movieModel.poster_path}")
            .into(binding.posterImage)


    }
}