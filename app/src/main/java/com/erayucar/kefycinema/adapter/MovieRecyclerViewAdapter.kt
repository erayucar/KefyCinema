package com.erayucar.kefycinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erayucar.kefycinema.databinding.RecyclerRowBinding
import com.erayucar.kefycinema.model.MovieModel

class MovieRecyclerViewAdapter(private val movieList: ArrayList<MovieModel>, private val listener : Listener) : RecyclerView.Adapter<MovieRecyclerViewAdapter.RowHolder>() {

    interface Listener{
        fun OnitemClick(movieModel: MovieModel)
    }



    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.OnitemClick(movieList.get(position))
        }


        holder.binding.textmovieName.text = movieList.get(position).original_title
        holder.binding.textVoteAverage.text = movieList.get(position).vote_average.toString()
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movieList.get(position).poster_path}")
            .into(holder.binding.posterImage)
    }
}