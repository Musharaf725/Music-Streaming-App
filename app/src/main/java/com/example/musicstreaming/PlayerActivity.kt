package com.example.musicstreaming

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide

class PlayerActivity : AppCompatActivity() {
    private lateinit var songTitleTextView: TextView
    private lateinit var songSubtitleTextView: TextView
    private lateinit var songCoverImageView: ImageView
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player)

        // Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        songTitleTextView = findViewById(R.id.song_title_text_view)
        songSubtitleTextView = findViewById(R.id.song_subtitle_text_view)
        songCoverImageView = findViewById(R.id.song_cover_image_view)
        playerView = findViewById(R.id.playerView)

        // Setup ExoPlayer and UI
        MyExoplayer.getCurrentSong()?.apply {
            songTitleTextView.text = title
            songSubtitleTextView.text = subtitle

            Glide.with(this@PlayerActivity)
                .load(coverUrl)
                .circleCrop()
                .into(songCoverImageView)

            exoPlayer = MyExoplayer.getInstance()!!
            playerView.player = exoPlayer

            // Show song controller
            playerView.showController()
        }
    }
}