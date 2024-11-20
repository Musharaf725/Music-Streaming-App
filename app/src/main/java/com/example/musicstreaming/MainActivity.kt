package com.example.musicstreaming

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstreaming.adapter.CategoryAdapter
import com.example.musicstreaming.adapter.SectionSongListAdapter
import com.example.musicstreaming.models.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var categoriesRecyclerView:RecyclerView
    private lateinit var section1RecyclerView: RecyclerView
    private lateinit var section1Title:TextView
    private lateinit var sectionRelativeLayout: RelativeLayout
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        categoriesRecyclerView= findViewById(R.id.categories_rv)
        section1RecyclerView= findViewById(R.id.section_1_rv)
        section1Title= findViewById(R.id.section_1_title)
        sectionRelativeLayout= findViewById(R.id.section_relativeLayout)

        //Fetch data and set up UI
        fetchCategories()
        setupSectionData()
    }

    //Fetch and sets up the list of categories from Firestore.
    private fun fetchCategories(){
        FirebaseFirestore.getInstance().collection("category")
            .get()
            .addOnSuccessListener { documents ->
                val categoryList = documents.toObjects(CategoryModel::class.java)
                setupCategoryRecyclerView(categoryList)
            }
    }

    //Set up the category RecyclerView with fetched data.
    private fun setupCategoryRecyclerView(categoryList: List<CategoryModel>){
        categoryAdapter = CategoryAdapter(categoryList)
        categoriesRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecyclerView.adapter=categoryAdapter
    }

    //Fetches and sets up the section data from Firestore.
    private fun setupSectionData() {
        FirebaseFirestore.getInstance().collection("sections")
            .document("section_1")
            .get()
            .addOnSuccessListener { document ->
                val section = document.toObject(CategoryModel::class.java)
                section?.apply {
                    section1Title.text= name
                    section1RecyclerView.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    section1RecyclerView.adapter= SectionSongListAdapter(songs)

                    sectionRelativeLayout.setOnClickListener {
                        SongsListActivity.category = this
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                    }
                }
            }
    }
}