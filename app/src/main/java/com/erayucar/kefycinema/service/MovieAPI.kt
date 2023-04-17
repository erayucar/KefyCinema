package com.erayucar.kefycinema.service

import com.erayucar.kefycinema.model.ResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

//https://api.themoviedb.org/3/movie/top_rated?api_key=f2b2065043e9ffe36d13d227205c1426&language=en-US&page=1
    //"/3/movie/top_rated?api_key=63077c882e430b993c36d739549dde55&language=en-US&page=1"

    @GET("/3/movie/{category}")
    fun getData(
        @Path("category") category: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,

    ): Call<ResultModel>


}