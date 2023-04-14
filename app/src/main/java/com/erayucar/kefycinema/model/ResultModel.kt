package com.erayucar.kefycinema.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResultModel(

     @SerializedName("page")
     @Expose
     val page: Int,
     @SerializedName("results")
     @Expose
     val results: List<MovieModel>,
     @SerializedName("total_pages")
     @Expose
     val totalPages : Int,
     @SerializedName("total_results")
     @Expose
     val totalResults : Int,

     ) : java.io.Serializable