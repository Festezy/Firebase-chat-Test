package com.ariqandrean.firebasechat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqandrean.firebasechat.R
import com.ariqandrean.firebasechat.adapter.UserAdapter
import com.ariqandrean.firebasechat.databinding.ActivityUserBinding
import com.ariqandrean.firebasechat.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    private var userList = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.UserRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        getUserList()
    }

    private fun getUserList(){
        var firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (dataSnapShot: DataSnapshot in snapshot.children){
                    val user = dataSnapShot.getValue(UserModel::class.java)

                    if (user!!.userId.equals(firebase.uid)){
                        userList.add(user)
                    }
                }
                var userAdapter = UserAdapter(this@UserActivity, userList)

                binding.UserRecyclerView.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_LONG).show()
            }
        })
    }
}