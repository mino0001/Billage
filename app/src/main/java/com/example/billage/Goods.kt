package com.example.billage

import android.os.Parcel
import android.os.Parcelable

data class Goods(
    val id: String, //d_id
    var img_goods: Int,
    var alias: String, //d_name
    var model: String, //d_model
    var more: String, //d_info
    var state: Int, //d_state
    var category: String, //c_name
    var os: String, //c_id
    var token: Int, //d_token
    var is_checked: Boolean
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
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(img_goods)
        parcel.writeString(alias)
        parcel.writeString(model)
        parcel.writeString(more)
        parcel.writeInt(state)
        parcel.writeString(category)
        parcel.writeString(os)
        parcel.writeInt(token)
        parcel.writeByte(if (is_checked) 1 else 0)
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
