package com.gigaworks.tech.geticons.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    val isPremium: Boolean,
    val price: String,
    val iconId: Int,
    val type: String,
    val name: String,
    val imgUrl: String
) : Parcelable