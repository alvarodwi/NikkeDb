package me.varoa.nikkedb.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.varoa.nikkedb.core.data.local.dao.FavoriteDao
import me.varoa.nikkedb.core.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}
