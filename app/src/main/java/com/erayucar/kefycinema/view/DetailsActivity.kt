package com.erayucar.kefycinema.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        binding.backButton.setOnClickListener {
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadIntoLayout()
        binding.bookmarkSaved.visibility = View.INVISIBLE
        binding.bookmarkSaved.setOnClickListener {
            onBookmarkClicked()
        }
        binding.bookmarkUnsaved.setOnClickListener {
            onBookmarkClicked()

        }

    }

      private  fun loadIntoLayout(){
        movieModel = intent.extras!!.getParcelable("movie")!!
        binding.textOriginalTitle.text = movieModel.original_title
        binding.language.text = movieModel.original_language
        binding.movieOverview.text = movieModel.overview
        binding.releaseDate.text = movieModel.release_date
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movieModel.poster_path}")
            .into(binding.posterImage)



    }

    private fun onBookmarkClicked() {


        if (binding.bookmarkSaved.visibility == View.VISIBLE) {
            // If imageButton1 is currently visible, make it invisible and make imageButton2 visible
            binding.bookmarkSaved.visibility= View.INVISIBLE
            binding.bookmarkUnsaved.visibility = View.VISIBLE
        } else {
            // If imageButton1 is currently invisible, make it visible and make imageButton2 invisible
            binding.bookmarkSaved.visibility= View.VISIBLE
            binding.bookmarkUnsaved.visibility = View.INVISIBLE
        }
    }

}