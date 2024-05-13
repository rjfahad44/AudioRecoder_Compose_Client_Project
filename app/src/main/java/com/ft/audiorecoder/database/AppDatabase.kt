package com.ft.audiorecoder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ft.audiorecoder.database.model.AudioRecord

@Database(entities = [AudioRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val dao: AudioRecordDao
}