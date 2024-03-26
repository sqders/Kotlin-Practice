@file:OptIn(ExperimentalSerializationApi::class)

package com.example.personalmanager.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.encodeToString


@Serializable
data class Note(
    @EncodeDefault val title: String = "",
    @EncodeDefault val noteDescription: String = "",
    @EncodeDefault var id: Int = -1
){
    override fun toString(): String = encodeToString(serializer= serializer(),this)
}