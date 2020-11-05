package com.example.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var okHttpClient: OkHttpClient = OkHttpClient()
    private lateinit var recyclerView : RecyclerView
    private var context1 : Context? = null
    private lateinit var view1 : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        refresh("https://api.themoviedb.org/3/movie/upcoming?api_key=${BuildConfig.API_KEY}&language=ru&page=1&region=ru")
    }

    fun refresh(URL : String) {


        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("Error", e.toString())
            }

            var articleList : ArrayList<ItemOfList> = ArrayList<ItemOfList>()
            override fun onResponse(call: Call, response: Response) {
                val json = (JSONObject(response.body()!!.string()))
                activity?.runOnUiThread {
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
                    recyclerView.adapter = ItemAdapter(context1, articleList) {
                        val intent = Intent(context1, DetailActivity::class.java)
                        intent.putExtra("OBJECT_INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        view1 = inflater.inflate(R.layout.fragment_coming, container, false)
        context1 = context
        recyclerView = view1.findViewById<RecyclerView>(R.id._RecyclerView)
        recyclerView.setHasFixedSize(true)
        var list = ArrayList<ItemOfList>()
        recyclerView.adapter = ItemAdapter(context1, list) {}
        // Inflate the layout for this fragment
        return view1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ComingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
