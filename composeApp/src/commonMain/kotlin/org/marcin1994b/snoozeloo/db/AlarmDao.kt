package org.marcin1994b.snoozeloo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AlarmDao {

    @Upsert
    suspend fun insert(alarm: Alarm)

    @Query("SELECT * FROM Alarm WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm?

    @Query("SELECT * FROM Alarm")
    suspend fun getAllAlarms(): List<Alarm>

    @Delete
    suspend fun delete(alarm: Alarm)

}