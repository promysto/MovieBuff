package com.example.moviebuff

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

//API Key
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MovieShowingFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        //AsyncHTTPClient
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        client [
                "https://api.themoviedb.org/3/movie/now_playing" , params,
                object: JsonHttpResponseHandler()
                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models
                        //results is a jsonArray, how to handle
                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                        val moviesrawJSON : String = resultsJSON.toString()

                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<MovieShowing>>() {}.type

                        val models : List<MovieShowing> = gson.fromJson(moviesrawJSON, arrayMovieType) // Fix me!
                        recyclerView.adapter = MovieShowingsRecyclerViewAdapter(models, this@MovieShowingFragment)

                        // Look for this in Logcat:
                        Log.d("MovieShowingFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("BestSellerBooksFragment", errorResponse)
                        }
                    }
                }]
    }

    //what will happen when movie is clicked
    override fun onItemClick(item: MovieShowing) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }
}