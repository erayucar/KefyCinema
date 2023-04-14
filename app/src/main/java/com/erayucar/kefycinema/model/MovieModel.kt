package com.erayucar.kefycinema.model

import android.os.Parcel
import android.os.Parcelable

data class MovieModel (
    val  backdrop_path: String,
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
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdrop_path)
        parcel.writeInt(id)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeString(title)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
    }

    override fun describeContents(): Int {
        return 0
    }

}