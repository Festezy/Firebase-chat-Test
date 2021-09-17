package com.ariqandrean.firebasechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ariqandrean.firebasechat.R
import com.ariqandrean.firebasechat.model.UserModel
import com.bumptech.glide.Glide

class UserAdapter(private val context: Context, private val userList: ArrayList<UserModel>)
    :RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val itemUserName:TextView = view.findViewById(R.id.itemName)
        val itemTemp: TextView = view.findViewById(R.id.itemTemp)
        val itemImage: ImageView = view.findViewById(R.id.itemImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.itemUserName.text = user.userName
        Glide.with(context).load(user.userImage).into(holder.itemImage)
    }
}