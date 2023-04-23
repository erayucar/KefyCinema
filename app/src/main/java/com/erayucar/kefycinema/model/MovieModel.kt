package com.erayucar.kefycinema.model

import android.os.Parcel
import android.os.Parcelable

data class MovieModel (

    val  id : Int,
    val  original_language:String,
    val  original_title: String,
    val  overview: String,
    val  poster_path: String,
    val  release_date: String,
    val  vote_average: Double,

): Parcelable{


    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<MovieModel> {
            override fun createFromParcel(parcel: Parcel): MovieModel {
                return MovieModel(parcel)
            }

            override fun newArray(size: Int): Array<MovieModel?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeDouble(vote_average)
    }

    override fun describeContents(): Int {
        return 0
    }

}