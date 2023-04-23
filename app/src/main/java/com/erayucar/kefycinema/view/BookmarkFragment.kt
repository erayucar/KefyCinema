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
import com.erayucar.kefycinema.databinding.FragmentBookmarkBinding
import com.erayucar.kefycinema.model.MovieModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookmarkFragment : Fragment(), MovieRecyclerViewAdapter.Listener {
    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    private lateinit var moviesList: ArrayList<MovieModel>
    private var movieRecyclerViewAdapter: MovieRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        currentUser = auth.currentUser!!
        firestore = Firebase.firestore
        moviesList = ArrayList<MovieModel>()

        getDataFromFirestore()
        movieRecyclerViewAdapter = MovieRecyclerViewAdapter(moviesList,this)






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
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = movieRecyclerViewAdapter
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

    private fun getDataFromFirestore(){
        firestore.collection("Users")
            .document(currentUser.uid)
            .collection("Bookmarks")
            .addSnapshotListener { value, error ->

                if(error != null){
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null){
                        if (!value.isEmpty){
                            val documents = value.documents

                            for(document in documents){
                                val documentid = document.get("id") as Long
                                val id = documentid.toInt()
                                val originalTitle = document.get("original_title") as String
                                val originalLanguage = document.get("original_language") as String
                                val overview = document.get("overview") as String
                                val posterPath = document.get("poster_path") as String
                                val releaseDate = document.get("release_date") as String
                                val voteAverage = document.get("vote_average") as Double

                                val movie = MovieModel(id, originalLanguage,originalTitle,overview,posterPath,releaseDate,voteAverage)
                                moviesList.add(movie)


                            }

                                movieRecyclerViewAdapter!!.notifyDataSetChanged()

                        }

                    }



                }




            }


    }
}


