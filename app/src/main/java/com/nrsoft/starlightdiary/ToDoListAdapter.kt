package com.nrsoft.starlightdiary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ToDoListAdapter constructor(val context: Context, var items:MutableList<ToDoListItem>) : RecyclerView.Adapter<ToDoListAdapter.VH>(){

    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvTitle:TextView by lazy { itemView.findViewById(R.id.tv_title)}
        val iv:ImageView by lazy { itemView.findViewById(R.id.iv) }
        val tvMsg:TextView by lazy { itemView.findViewById(R.id.tv_msg) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.VH {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView= inflater.inflate(R.layout.recycler_item_todolist, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.VH, position: Int) {
        val item= items.get(position)

        holder.tvTitle.text=item.title
        holder.tvMsg.text=item.message
        Glide.with(context).load("http://yn1016.dothome.co.kr/DiaryRetrofit/"+item.image).into(holder.iv)



    }

    override fun getItemCount(): Int= items.size


}