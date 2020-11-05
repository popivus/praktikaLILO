package com.example.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemOfList(
    val title : String,
    val desc : String,
    val urlToImage : String) : Parcelable
