package com.erayucar.kefycinema.model

data class ResultsModel(
    val  backdrop_path: String,
    val  genre_ids : Int,
    val  id : Int,
    val  original_language:String,
    val  original_title: String,
    val  overview: String,
    val  popularity: Double,
    val  poster_path: String,
    val  release_date: String,
    val  title: String,
    val  vote_average: Double,
    val  vote_count: Int,

)