package com.example.binexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.binexplorer.data.local.history.BinHistoryDao
import com.example.binexplorer.data.local.history.BinHistoryEntity

@Database(
    entities = [BinHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun binHistoryDao(): BinHistoryDao
}