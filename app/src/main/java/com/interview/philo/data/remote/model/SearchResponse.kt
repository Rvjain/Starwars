package com.interview.philo.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Result>
) {
    @Parcelize
    data class Result(
        @SerializedName("birth_year")
        val birthYear: String,
        @SerializedName("created")
        val created: String,
        @SerializedName("edited")
        val edited: String,
        @SerializedName("eye_color")
        val eyeColor: String,
        @SerializedName("films")
        val films: List<String>,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("hair_color")
        val hairColor: String,
        @SerializedName("height")
        val height: String,
        @SerializedName("homeworld")
        val homeworld: String,
        @SerializedName("mass")
        val mass: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("skin_color")
        val skinColor: String,
        @SerializedName("species")
        val species: List<String>,
        @SerializedName("starships")
        val starships: List<String?>,
        @SerializedName("url")
        val url: String,
        @SerializedName("vehicles")
        val vehicles: List<String?>
    ) : Parcelable
}