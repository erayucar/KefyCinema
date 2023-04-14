package com.erayucar.kefycinema.service

import com.erayucar.kefycinema.model.ResultModel
import retrofit2.Call
import retrofit2.http.GET

interface MovieAPI {

//https://api.themoviedb.org/3/movie/top_rated?api_key=f2b2065043e9ffe36d13d227205c1426&language=en-US&page=1

    @GET("/3/movie/top_rated?api_key=63077c882e430b993c36d739549dde55&language=en-US&page=1")
    fun getData(): Call<ResultModel>


}