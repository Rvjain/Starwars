package com.interview.philo.view.item

import android.os.Parcelable
import com.interview.philo.data.remote.model.SearchResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItem(val result: SearchResponse.Result) : Parcelable {
    companion object {
        fun modelToItem(results: List<SearchResponse.Result>) : List<SearchItem> {
            return results.map {
                SearchItem(it)
            }
        }
    }
}