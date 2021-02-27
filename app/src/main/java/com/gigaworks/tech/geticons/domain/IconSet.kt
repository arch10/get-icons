package com.gigaworks.tech.geticons.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IconSet(
    val type: String,
    val name: String,
    val license: String,
    val price: String,
    val isPremium: Boolean,
    val id: Int,
    val readme: String,
    val authorName: String,
    val authorWebsite: String,
    val authorUsername: String,
    val authorId: Int
) : Parcelable