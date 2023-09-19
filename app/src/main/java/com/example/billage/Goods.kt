package com.example.billage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Goods (
    val id: Int,
    var img_goods: Int,
    var alias: String,
    var more: String,
    var category: String,
    var os: Int,
    var is_checked : Boolean
    ) : Parcelable