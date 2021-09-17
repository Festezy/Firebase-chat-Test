package com.ariqandrean.firebasechat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ariqandrean.firebasechat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signinButton.setOnClickListener {
            val email = binding.signinEmailEditText.text.toString()
            val password = binding.signinPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful){
                            binding.signinProgressBar.visibility = View.VISIBLE

                            binding.signinEmailEditText.setText("")
                            binding.signinEmailEditText.setText("")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            binding.signinProgressBar.visibility = View.INVISIBLE
                            finish()
                        } else {
                            Toast.makeText(this, "email or password Invalid", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "email or password Required", Toast.LENGTH_SHORT).show()
            }

        }
        binding.signinSignupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}