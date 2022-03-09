package com.example.noteitallapp.note_feature.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteitallapp.note_feature.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao : notedao // gives access of the functions in note Dao

    companion object {
        const val DATABASE_NAME = "notes_database"
    }
}