package com.erayucar.kefycinema.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide

import com.erayucar.kefycinema.databinding.ActivityDetailsBinding
import com.erayucar.kefycinema.model.MovieModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var movieModel: MovieModel
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private  var isInMyBookmark : Boolean? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        firestore = Firebase.firestore
        auth = Firebase.auth
        currentUser = auth.currentUser!!


        isInMyBookmark = false









        loadIntoLayout()
        onBookmarkClicked()


        binding.bookmarkSaved.setOnClickListener {
            // remove movie
            deleteFromFirestore()        }
        binding.bookmarkUnsaved.setOnClickListener {
            //add movie
            putDataToFirestore()



        }

    }

      private  fun loadIntoLayout(){
        movieModel = intent.extras!!.getParcelable("movie")!!
        binding.textOriginalTitle.text = movieModel.original_title
          binding.textVoteAverage.text = movieModel.vote_average.toString()
        binding.language.text = movieModel.original_language
        binding.movieOverview.text = movieModel.overview

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(movieModel.release_date.toString())
        val formatDate = SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH).format(date)
        binding.releaseDate.text = formatDate


        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movieModel.poster_path}")
            .into(binding.posterImage)



    }

    private fun onBookmarkClicked() {

        firestore.collection("Users").document(currentUser.uid)
            .collection("Bookmarks")
            .document(movieModel.id.toString())
            .addSnapshotListener { value, error ->
               isInMyBookmark = value!!.exists()
                if (isInMyBookmark!!){

                    binding.bookmarkSaved.visibility= View.VISIBLE
                    binding.bookmarkUnsaved.visibility = View.INVISIBLE

                }else{
                    binding.bookmarkSaved.visibility= View.INVISIBLE
                    binding.bookmarkUnsaved.visibility = View.VISIBLE

                }

            }

    }

    private fun putDataToFirestore(){
        val movieMap = hashMapOf<String,Any>()
            movieMap.put("id",movieModel.id)
            movieMap.put("original_title",movieModel.original_title)
            movieMap.put("original_language",movieModel.original_language)
            movieMap.put("overview",movieModel.overview)
            movieMap.put("poster_path",movieModel.poster_path)
            movieMap.put("release_date",movieModel.release_date)
            movieMap.put("vote_average",movieModel.vote_average)

            firestore.collection("Users").document(currentUser.uid).collection("Bookmarks").document(movieModel.id.toString()).set(movieMap).addOnSuccessListener {
                Toast.makeText(this@DetailsActivity, "Movie added to Bookmarks", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@DetailsActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }

    }
    private fun deleteFromFirestore(){

        firestore.collection("Users").document(currentUser.uid).collection("Bookmarks").document(movieModel.id.toString()).delete().addOnSuccessListener {
            Toast.makeText(this@DetailsActivity, "Movie removed from Bookmarks", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this@DetailsActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

}