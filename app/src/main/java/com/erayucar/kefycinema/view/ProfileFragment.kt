package com.erayucar.kefycinema.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null){
            binding.textProfileEmail.text = currentUser.email.toString()


        }


        binding.logout.setOnClickListener {
            signOut()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun signOut(){
        auth.signOut()
        if (activity != null){
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }
    }

    }
