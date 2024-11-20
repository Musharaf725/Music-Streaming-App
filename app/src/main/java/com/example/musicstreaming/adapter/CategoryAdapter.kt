package com.example.musicstreaming.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreaming.R
import com.example.musicstreaming.SongsListActivity
import com.example.musicstreaming.models.CategoryModel

class CategoryAdapter(private val categoryList: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val nameTextView: TextView= view.findViewById(R.id.name_text_view)
        private val coverImageView: ImageView= view.findViewById(R.id.cover_image_view)

        fun bindData(context: Context, category: CategoryModel){
            nameTextView.text= category.name
            Glide.with(context)
                .load(category.coverUrl)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(coverImageView)

            //Start SongsListActivity on item click
            itemView.setOnClickListener {
                SongsListActivity.category=category
                context.startActivity(Intent(context, SongsListActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.category_item_recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(holder.itemView.context, categoryList[position])
    }
}