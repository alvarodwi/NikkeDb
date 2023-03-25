package me.varoa.nikkedb.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String
)
