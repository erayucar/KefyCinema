package com.erayucar.kefycinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
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
    private var PAGE : Int = 1
    private var API_KEY: String ="63077c882e430b993c36d739549dde55"
    private var LANGUAGE: String ="en-US"
    private var CATEGORY : String = "top_rated"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.textPage.text = PAGE.toString()


        replaceFragment(HomeFragment())
        loadData()
        fragmentExecute()

    }



    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val  fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    private fun fragmentExecute(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    binding.upperPageButton.visibility = View.VISIBLE
                    binding.subPageButton.visibility = View.VISIBLE
                    binding.textPage.visibility = View.VISIBLE
                    PAGE = 1
                    binding.textPage.text = PAGE.toString()

                    binding.upperPageButton.setOnClickListener {
                        PAGE++
                        binding.textPage.text = PAGE.toString()
                        loadData()
                    }
                    binding.subPageButton.setOnClickListener {
                        if (PAGE > 1){
                            PAGE--
                            binding.textPage.text = PAGE.toString()
                            loadData()
                        }


                    }
                    replaceFragment(HomeFragment())
                    loadData()

                }

                R.id.bookmark -> {
                    binding.upperPageButton.visibility = View.GONE
                    binding.subPageButton.visibility = View.GONE
                    binding.textPage.visibility = View.GONE

                    replaceFragment(BookmarkFragment())
                }
                R.id.profile -> {
                    binding.upperPageButton.visibility = View.GONE
                    binding.subPageButton.visibility = View.GONE
                    binding.textPage.visibility = View.GONE

                    replaceFragment(ProfileFragment())
                }
                else -> {}
            }
            true
        }





    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieAPI::class.java)
        val call = service.getData(CATEGORY,API_KEY,LANGUAGE,PAGE)

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

