package com.ariqandrean.firebasechat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ariqandrean.firebasechat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.SignupButton.setOnClickListener {
            binding.signupProgressBar.visibility = View.VISIBLE
            val userName = binding.signupNameEditText.text.toString()
            val email = binding.signupEmailEditText.text.toString()
            val password = binding.signupPasswordEditText.text.toString()
            val confirmPassword = binding.signupConfirmPasswordEditText.text.toString()

            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                Toast.makeText(this, "Login Successfuly", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            registerUser(userName, email, password)
        }
        binding.signupProgressBar.visibility = View.INVISIBLE
    }

    private fun registerUser(userName: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ it ->
                if (it.isSuccessful){
                    var user: FirebaseUser? = auth.currentUser
                    var userId: String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId)

                    var hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("profileImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener { it ->
                        if (it.isSuccessful){
//                            open Mainactivity
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
    }
}