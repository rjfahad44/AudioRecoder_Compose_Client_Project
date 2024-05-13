package com.ft.audiorecoder.database

import androidx.room.*
import com.ft.audiorecoder.database.model.AudioRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioRecordDao {
    @Query(
        "SELECT * FROM audioRecords WHERE filename LIKE '%' || :searchQuery || '%'"
    )
    fun getSearchRecords(
        searchQuery: String
    ): Flow<List<AudioRecord>>


    @Query("SELECT * FROM audioRecords")
    fun getRecords(): Flow<List<AudioRecord>>

    @Query("DELETE FROM audioRecords")
    fun deleteAll()

    @Insert
    fun insert(vararg audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecords: List<AudioRecord>)

    @Update
    fun update(audioRecord: AudioRecord)
}