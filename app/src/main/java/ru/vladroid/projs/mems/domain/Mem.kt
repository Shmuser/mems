package ru.vladroid.projs.mems.domain

import android.os.Parcel
import android.os.Parcelable

data class Mem(
    val id: Long,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val createdDate: Long,
    val photoUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean(),
        parcel.readLong(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeBoolean(isFavorite)
        parcel.writeLong(createdDate)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mem> {
        override fun createFromParcel(parcel: Parcel): Mem {
            return Mem(parcel)
        }

        override fun newArray(size: Int): Array<Mem?> {
            return arrayOfNulls(size)
        }
    }
}