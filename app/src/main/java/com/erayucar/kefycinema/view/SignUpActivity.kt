package com.erayucar.kefycinema.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.erayucar.kefycinema.R
import com.erayucar.kefycinema.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

    }

    fun signUp(view : View){
        val email = binding.signupTextEmail.text.toString()
        val password = binding.signUpTextPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //Success
                val intent = Intent(this@SignUpActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                //Failure
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show()
        }

    }
}