package com.example.musicstreaming.adapter

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
import com.example.musicstreaming.MyExoplayer
import com.example.musicstreaming.PlayerActivity
import com.example.musicstreaming.R
import com.example.musicstreaming.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore

class SectionSongListAdapter(private val songIdList: List<String>) :
    RecyclerView.Adapter<SectionSongListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songTitleTextView: TextView = itemView.findViewById(R.id.song_title_text_view)
        private val songSubtitleTextView: TextView = itemView.findViewById(R.id.song_subtitle_text_view)
        private val songCoverImageView: ImageView = itemView.findViewById(R.id.song_cover_image_view)

        // Bind data with views
        fun bindData(songId: String) {
            FirebaseFirestore.getInstance().collection("songs")
                .document(songId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val song = documentSnapshot.toObject(SongModel::class.java)
                    song?.apply {
                        songTitleTextView.text = title
                        songSubtitleTextView.text = subtitle

                        Glide.with(songCoverImageView.context)
                            .load(coverUrl)
                            .apply(RequestOptions().transform(RoundedCorners(32)))
                            .into(songCoverImageView)

                        itemView.setOnClickListener {
                            MyExoplayer.startPlaying(itemView.context, this)
                            it.context.startActivity(Intent(it.context, PlayerActivity::class.java))
                        }
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_section_recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }
}