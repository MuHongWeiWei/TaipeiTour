package com.example.taipeitour

import com.google.gson.annotations.SerializedName

data class AttractionsRec(
    @SerializedName("data") val data: List<Attractions>
)

data class Attractions(
    val id: Int,
    val name: String,
    val name_zh: String,
    val open_status: Int,
    val introduction: String,
    val open_time: String,
    val zipcode: String,
    val distric: String,
    val address: String,
    val tel: String,
    val fax: String,
    val email: String,
    val months: String,
    @SerializedName("nlat")
    val latitude: Double,
    @SerializedName("elong")
    val longitude: Float,
    val official_site: String,
    val facebook: String,
    val ticket: String,
    val remind: String,
    @SerializedName("staytime")
    val stayTime: String,
    val modified: String,
    val url: String,
    val category: List<AttractionsCategory>,
    val target: List<AttractionsTarget>,
    val service: List<String>,
    val friendly: List<String>,
    val images: List<String>,
    val files: List<String>,
    val links: List<AttractionsLinks>,

    )

data class AttractionsCategory(
    val id: Int,
    val name: String
)

data class AttractionsTarget(
    val id: Int,
    val name: String
)

data class AttractionsLinks(
    val src: Int,
    val subject: String
)