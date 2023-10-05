package com.example.billage

import android.os.Parcel
import android.os.Parcelable

data class Goods(
    val id: String, //d_id
    var img_goods: Int,
    var name: String, //d_name
    var model: String, //d_model
    var more: String, //d_info
    var state: Int, //d_state
    var c_name: String, //c_name
    var c_id: String, //c_id
    var token: Int, //d_token
    val rental_count: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",

        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(img_goods)
        parcel.writeString(name)
        parcel.writeString(model)
        parcel.writeString(more)
        parcel.writeInt(state)
        parcel.writeString(c_name)
        parcel.writeString(c_id)
        parcel.writeInt(token)
        parcel.writeString(rental_count)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Goods> {
        override fun createFromParcel(parcel: Parcel): Goods {
            return Goods(parcel)
        }

        override fun newArray(size: Int): Array<Goods?> {
            return arrayOfNulls(size)
        }
    }
}
