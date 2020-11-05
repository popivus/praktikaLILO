package com.example.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ItemAdapter (private val context : Context?,
                   private val articles: ArrayList<ItemOfList>,
                   val listener: (ItemOfList) -> Unit) : RecyclerView.Adapter<ItemAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id._image)
        val title = view.findViewById<TextView>(R.id.title)
        val description = view.findViewById<TextView>(R.id.desc)

        fun bindView(article: ItemOfList, listener: (ItemOfList) -> Unit) {
            title.text = article.title
            description.text = article.desc

            itemView.setOnClickListener { listener(article) }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var item = articles.get(position)
        Picasso.with(context).load(item.urlToImage).fit().centerInside()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground).fit().into(holder.image)
        holder.bindView(articles[position], listener)
    }
}