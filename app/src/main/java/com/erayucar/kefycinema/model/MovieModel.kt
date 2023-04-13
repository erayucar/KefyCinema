package com.erayucar.kefycinema.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieModel(

     @SerializedName("page")
     @Expose
     val page: Int,
     @SerializedName("results")
     @Expose
     val results: List<ResultsModel>,
     @SerializedName("total_pages")
     @Expose
     val totalPages : Int,
     @SerializedName("total_results")
     @Expose
     val totalResults : Int,

     )