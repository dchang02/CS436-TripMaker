package com.example.generateinitialitinerary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.example.generateinitialitinerary.databinding.ActivityLoginBinding
import java.util.logging.Logger


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()


        binding.textView6.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailField.text.toString()
            val pass = binding.passwordField.text.toString()
            Log.i("info", "login click")

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                Log.i("info", "login not empty")
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i("info", "success")
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("info", "nope")
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        Log.i("info", it.exception.toString())

                    }
                }


                Log.i("info", "after")
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            firebaseAuth.signOut()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}