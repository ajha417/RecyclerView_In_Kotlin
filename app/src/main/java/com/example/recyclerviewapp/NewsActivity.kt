package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsHeading: TextView = findViewById(R.id.headingss)
        val title_images: ImageView =  findViewById(R.id.news_image)
        val main_news: TextView = findViewById(R.id.main_news)

        val bundle: Bundle? = intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("imageId")
        val news = bundle.getString("news")

        newsHeading.text = heading
        main_news.text = news
        title_images.setImageResource(imageId)
    }
}