package com.caririfest.data.datasource.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.caririfest.network.model.Document
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val desc: String,
    val date: String,
    val img: String,
    val link: String,
    val location: String,
    val place: String,
    val time: String,
    val favorite: Boolean,
    val hot: Boolean
) : Parcelable


fun Document.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = fields.title.stringValue,
        desc = fields.desc.stringValue,
        date = fields.date.stringValue,
        img = fields.img.stringValue,
        location = fields.location.stringValue,
        place = fields.place.stringValue,
        time = fields.time.stringValue,
        favorite = fields.favorite.booleanValue,
        hot = fields.hot.booleanValue,
        link = fields.link.stringValue
    )
}

