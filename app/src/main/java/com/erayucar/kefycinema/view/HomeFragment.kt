package com.erayucar.kefycinema.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.erayucar.kefycinema.databinding.FragmentHomeBinding
import com.erayucar.kefycinema.model.MovieModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var moviesList : ArrayList<MovieModel>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            moviesList = it.getParcelableArrayList<MovieModel>("results")!!

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}