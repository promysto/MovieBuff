package com.example.moviebuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieShowingsRecyclerViewAdapter (
    private val movies: List<MovieShowing>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<MovieShowingsRecyclerViewAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: MovieShowing? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movieTitle) as TextView
        val mMovieDescription: TextView =
            mView.findViewById<View>(R.id.movieDescription) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(R.id.movieImage) as ImageView

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val image_URL_prefix = "https://image.tmdb.org/t/p/w500"

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        Glide.with(holder.mView)
            .load(image_URL_prefix + movie.path)
            .centerInside()
            .into(holder.mMovieImage)
        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }
    }

        override fun getItemCount(): Int {
            return movies.size
        }
    }
