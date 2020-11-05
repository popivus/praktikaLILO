package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<ItemOfList>("OBJECT_INTENT")

        val img = findViewById<ImageView>(R.id.imageDetail)
        val title = findViewById<TextView>(R.id.titleDetail)
        val desc = findViewById<TextView>(R.id.descDetail)
        val content = findViewById<TextView>(R.id.contentDetail)
        val date = findViewById<TextView>(R.id.dateDetail)
        val author = findViewById<TextView>(R.id.authorDetail)

        Picasso.with(this).load(item.urlToImage).fit().centerInside()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground).fit().into(img)
        title.text = item.title
        desc.text = item.desc
    }
}