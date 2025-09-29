package com.caririfest.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
data class EventResponse(
    val documents: List<Document> = emptyList()
)

@OptIn(ExperimentalSerializationApi::class)
@JsonIgnoreUnknownKeys
@Serializable
data class Document(
    val name: String,
    val fields: EventFields
){
    val id: String
        get() = name.substringAfterLast("/")
}

@Serializable
data class EventFields(
    val date: FirestoreString,
    val desc: FirestoreString,
    val favorite: FirestoreBoolean,
    val img: FirestoreString,
    val link: FirestoreString,
    val location: FirestoreString,
    val place: FirestoreString,
    val time: FirestoreString,
    val title: FirestoreString
)

@Serializable
data class FirestoreString(
    val stringValue: String = ""
)

@Serializable
data class FirestoreBoolean(
    val booleanValue: Boolean
)