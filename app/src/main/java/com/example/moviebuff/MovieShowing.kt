package com.example.moviebuff

import com.google.gson.annotations.SerializedName

class MovieShowing {
    //add title, description, image
    @JvmField
    @SerializedName("original_title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var description: String? = null

    @JvmField
    @SerializedName("poster_path")
    var path: String? = null

}