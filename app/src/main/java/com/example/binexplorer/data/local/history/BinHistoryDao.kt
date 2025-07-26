package com.example.binexplorer.data.local.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BinHistoryDao {
    @Insert
    suspend fun insert(historyItem: BinHistoryEntity)

    @Query("SELECT * FROM bin_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BinHistoryEntity>>

    @Query("DELETE FROM bin_history")
    suspend fun clearAll()
}