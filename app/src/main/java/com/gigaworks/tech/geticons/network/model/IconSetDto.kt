package com.gigaworks.tech.geticons.network.model


import com.gigaworks.tech.geticons.domain.IconSet
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class IconSetDto(
    @SerializedName("author")
    val author: Author?,
    @SerializedName("icons_count")
    val iconsCount: Int,
    @SerializedName("iconset_id")
    val iconsetId: Int,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("license")
    val license: License?,
    @SerializedName("prices")
    val prices: List<Price>?,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("readme")
    val readme: String?
)

fun IconSetDto.toDomain(): IconSet {
    val authorName = author?.name
    val website = author?.website
    val license: String = if (isPremium) {
        prices?.get(0)?.license?.name ?: ""
    } else {
        license?.name ?: ""
    }
    val price = if (isPremium) {
        val p: Double = prices?.get(0)?.price ?: 0.0
        val decimalFormat = DecimalFormat("0.00")
        "$${decimalFormat.format(p)}"
    } else {
        "Free"
    }
    val readme = readme?:""

    return IconSet(
        type,
        name,
        license,
        price,
        isPremium,
        iconsetId,
        readme,
        authorName,
        website,
        author?.username,
        author?.userId
    )
}