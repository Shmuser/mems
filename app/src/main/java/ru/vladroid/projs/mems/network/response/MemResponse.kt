package ru.vladroid.projs.mems.network.response

data class MemResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val isFavorite: Boolean,
    val createdDate: Long,
    val photoUrl: String
)