package com.erayucar.kefycinema.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erayucar.kefycinema.adapter.MovieRecyclerViewAdapter
import com.erayucar.kefycinema.databinding.FragmentHomeBinding
import com.erayucar.kefycinema.model.MovieModel
import kotlin.concurrent.fixedRateTimer

class HomeFragment : Fragment(),MovieRecyclerViewAdapter.Listener{
    private var _binding: FragmentHomeBinding? = null
    private lateinit var moviesList : ArrayList<MovieModel>
    private var movieRecyclerViewAdapter: MovieRecyclerViewAdapter? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            moviesList = it.getParcelableArrayList<MovieModel>("results")!!
            println(moviesList.get(0).original_title)
            movieRecyclerViewAdapter = MovieRecyclerViewAdapter(moviesList,this)



        }





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = movieRecyclerViewAdapter


        upperAndSubPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(results: List<MovieModel>): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putParcelableArrayList("results", ArrayList(results))
            fragment.arguments = args
            return fragment
        }
    }

    fun upperAndSubPage(){
        binding.upperPageButton.setOnClickListener {
            Toast.makeText(context, "upperPage", Toast.LENGTH_SHORT).show()
        }

        binding.subPageButton.setOnClickListener {
            Toast.makeText(context, "Sub Page", Toast.LENGTH_SHORT).show()

        }
    }

    override fun OnitemClick(movieModel: MovieModel) {
        val intent = Intent(context,DetailsActivity::class.java)
        intent.putExtra("movie", movieModel)
        startActivity(intent)
    }
}