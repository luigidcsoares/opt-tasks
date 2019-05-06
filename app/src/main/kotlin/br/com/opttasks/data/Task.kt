package br.com.opttasks.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val name: String?,
    val level: Int?
) : Parcelable
