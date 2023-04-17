package com.erayucar.kefycinema.view

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.adapter.MovieRecyclerViewAdapter
import com.erayucar.kefycinema.databinding.FragmentBookmarkBinding
import com.erayucar.kefycinema.databinding.FragmentHomeBinding
import com.erayucar.kefycinema.model.MovieModel
class BookmarkFragment : Fragment(), MovieRecyclerViewAdapter.Listener {
    private var _binding: FragmentBookmarkBinding? = null


    private var moviesList: ArrayList<MovieModel>? =null
    private var movieRecyclerViewAdapter: MovieRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            val movieModel = it.getParcelable<MovieModel>("movie")
            if (movieModel != null) {
                println(movieModel.original_title)

                moviesList = ArrayList()
                moviesList!!.add(movieModel)
                movieRecyclerViewAdapter = MovieRecyclerViewAdapter(moviesList!!, this)
            }
        }


    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.bookmarkRecyclerView.layoutManager = layoutManager
        binding.bookmarkRecyclerView.adapter = movieRecyclerViewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnitemClick(movieModel: MovieModel) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("movie", movieModel)
        startActivity(intent)
    }

    companion object {
        fun newInstance(movie: MovieModel): BookmarkFragment {
            val fragment = BookmarkFragment()
            val args = Bundle()
            args.putParcelable("movie", movie)
            fragment.arguments = args
            return fragment
        }
    }
}


