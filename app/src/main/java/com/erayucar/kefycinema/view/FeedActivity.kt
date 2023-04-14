package com.erayucar.kefycinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.ActivityFeedBinding
import com.erayucar.kefycinema.model.ResultModel
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
    private var moviesList: List<MovieModel>? = null

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

        call.enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if (response.isSuccessful){
                    response.body().let {
                        moviesList = it!!.results
                        val fragment = HomeFragment.newInstance(moviesList!!)
                        replaceFragment(fragment)

                    }
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}

