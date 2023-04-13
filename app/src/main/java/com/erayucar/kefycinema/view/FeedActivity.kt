package com.erayucar.kefycinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.ActivityFeedBinding
import com.erayucar.kefycinema.model.MovieModel
import com.erayucar.kefycinema.service.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private val BASE_URL = "https://api.themoviedb.org"
    private var movieModels: ArrayList<MovieModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.bookmark -> replaceFragment(BookmarkFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }


        loadData()

    }



    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val  fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<MovieModel>> {
            override fun onResponse(
                call: Call<List<MovieModel>>,
                response: Response<List<MovieModel>>
            ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@FeedActivity, "success", Toast.LENGTH_SHORT).show()
                        response.body()?.let {
                            movieModels = ArrayList(it)
                            for (movieModel : MovieModel in movieModels!!){
                                println(movieModel.original_title)
                            }
                        }

                    }
            }

            override fun onFailure(call: Call<List<MovieModel>>, t: Throwable) {
                Toast.makeText(this@FeedActivity, "error!", Toast.LENGTH_SHORT).show()    
                t.printStackTrace()
            }


        })

    }

}