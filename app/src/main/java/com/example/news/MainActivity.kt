package com.example.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=${BuildConfig.API_KEY}&language=ru&page=1&region=ru"
    var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topFragment = TopFragment()
        val comingFragment = ComingFragment()
        makeCurrentFragment(comingFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.topFragment -> makeCurrentFragment(topFragment)
                R.id.comingFragment -> makeCurrentFragment(comingFragment)
            }
            true
        }
        //refresh(URL)
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_replacer, fragment)
            commit()
        }

    fun refresh(URL : String) {
        val recyclerView = findViewById<RecyclerView>(R.id._RecyclerView)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("Error", e.toString())
            }

            var articleList : ArrayList<ItemOfList> = ArrayList<ItemOfList>()
            override fun onResponse(call: Call, response: Response) {
                val json = (JSONObject(response.body()!!.string()))
                runOnUiThread {
                    val articleArray = json.getJSONArray("results")
                    for (i in 0..articleArray.length() - 1) {
                        var article = articleArray.optJSONObject(i)

                        var title = article.getString("title")
                        var desc = article.getString("overview")
                        var urlToImage = article.getString("poster_path")
                        /*var publishedAt = article.getString("publishedAt")
                        var content = article.getString("content")
                        var author = article.getString("author")*/

                        articleList.add(ItemOfList(title, desc, urlToImage))
                    }
                    recyclerView.adapter = ItemAdapter(this@MainActivity, articleList) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("OBJECT_INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })
    }

}