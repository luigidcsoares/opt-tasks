package br.com.opttasks.data.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val name: String?,
    val value: Int?
) : Parcelable
