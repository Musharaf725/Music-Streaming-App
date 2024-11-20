package com.example.musicstreaming

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreaming.adapter.SongListAdapter
import com.example.musicstreaming.models.CategoryModel

class SongsListActivity : AppCompatActivity() {
    companion object {
        lateinit var category: CategoryModel
    }

    private lateinit var nameTextView:TextView
    private lateinit var coverImageView:ImageView
    private lateinit var songsListRecyclerView:RecyclerView
    private lateinit var songListAdapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_songs_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameTextView= findViewById(R.id.name_text_view)
        coverImageView= findViewById(R.id.cover_image_view)
        songsListRecyclerView= findViewById(R.id.songs_list_recycler_view)

        // Set category details
        nameTextView.text= category.name
        Glide.with(this)
            .load(category.coverUrl)
            .apply(RequestOptions().transform(RoundedCorners(32)))
            .into(coverImageView)

        // Setup RecyclerView
        setupSongsListRecyclerView()
    }
    private fun setupSongsListRecyclerView(){
        songListAdapter= SongListAdapter(category.songs)
        songsListRecyclerView.layoutManager= LinearLayoutManager(this)
        songsListRecyclerView.adapter= songListAdapter
    }
}