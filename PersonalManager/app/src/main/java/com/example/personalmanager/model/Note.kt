package com.example.personalmanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val title: String = "",
    val noteDescription: String = "",
    var id: Int = -1
)