package org.marcin1994b.snoozeloo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AlarmDao {

    @Upsert
    suspend fun insert(alarm: AlarmEntity2)

    @Query("SELECT * FROM AlarmEntity2 WHERE id = :id")
    suspend fun getAlarmById(id: String): AlarmEntity2?

    @Query("SELECT * FROM AlarmEntity2")
    suspend fun getAllAlarms(): List<AlarmEntity2>

    @Delete
    suspend fun delete(alarm: AlarmEntity2)

}