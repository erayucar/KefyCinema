package com.erayucar.kefycinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.ActivityFeedBinding
import com.erayucar.kefycinema.model.MovieModel
import com.erayucar.kefycinema.service.MovieAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private val BASE_URL = "https://api.themoviedb.org"
    private var moviesList: List<MovieModel>? = null
    private var PAGE : Int = 1
    private var API_KEY: String ="63077c882e430b993c36d739549dde55"
    private var LANGUAGE: String ="en-US"
    private var CATEGORY : String = "now_playing"
    private var QUERY : String = ""
    private var ADULT : Boolean = false
    private var moviesSearchList: List<MovieModel>? = null
    private var loadJob : Job? = null
    private var searchJob : Job? = null


    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        pageBinding()


        replaceFragment(HomeFragment())
        loadData()
        fragmentExecute()

    }

    override fun onDestroy() {
        super.onDestroy()
        loadJob?.cancel()
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
                    binding.searcButton.visibility = View.VISIBLE
                    binding.seachBarText.visibility = View.VISIBLE

                    binding.seachBarText.setText("")


                    PAGE = 1
                    binding.textPage.text = PAGE.toString()
                    replaceFragment(HomeFragment())
                    loadData()

                }

                R.id.bookmark -> {
                    binding.upperPageButton.visibility = View.GONE
                    binding.subPageButton.visibility = View.GONE
                    binding.textPage.visibility = View.GONE
                    binding.searcButton.visibility = View.GONE
                    binding.seachBarText.visibility = View.GONE

                    replaceFragment(BookmarkFragment())
                }
                R.id.profile -> {
                    binding.upperPageButton.visibility = View.GONE
                    binding.subPageButton.visibility = View.GONE
                    binding.textPage.visibility = View.GONE
                    binding.searcButton.visibility = View.GONE
                    binding.seachBarText.visibility = View.GONE

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
            .build().create(MovieAPI::class.java)

        loadJob = CoroutineScope(Dispatchers.IO).launch {

            val response = retrofit.getData(CATEGORY,API_KEY,LANGUAGE,PAGE)

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body().let {
                        moviesList = it!!.results
                        val fragment = HomeFragment.newInstance(moviesList!!)
                        replaceFragment(fragment)

                    }
                }

            }
        }

    }

    private fun searchdata(){
         val retrofit = Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build().create(MovieAPI::class.java)

        searchJob = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.searchMovies(API_KEY,LANGUAGE,QUERY,PAGE,ADULT)

            withContext(Dispatchers.Main + exceptionHandler){
                if(response.isSuccessful){
                    response.body().let {
                        moviesSearchList = it!!.results
                        val fragment = HomeFragment.newInstance(moviesSearchList!!)
                        replaceFragment(fragment)
                    }
                }

            }

        }
       /* val service = retrofit.create(MovieAPI::class.java)
        val call = service.searchMovies(API_KEY,LANGUAGE,QUERY,PAGE,ADULT)

        call.enqueue(object : Callback<ResultModel>{
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if(response.isSuccessful){
                    response.body().let {
                        moviesSearchList = it!!.results
                        val fragment = HomeFragment.newInstance(moviesSearchList!!)
                        replaceFragment(fragment)
                    }
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@FeedActivity, "Search failed", Toast.LENGTH_SHORT).show()
            }


        }) */

    }

    fun searchMovie(view : View){
        QUERY = binding.seachBarText.text.toString()
        if (QUERY.isNotEmpty() || QUERY != ""){
            searchdata()
            binding.upperPageButton.visibility = View.GONE
            binding.subPageButton.visibility = View.GONE
            binding.textPage.visibility = View.GONE

        } else {
            Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
        }


    }

    private fun pageBinding(){
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

    }



}

