package com.example.noteitallapp.note_feature.data.dataSource

import androidx.room.*
import com.example.noteitallapp.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface notedao {

    @Query("SELECT * from Note")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * from Note WHERE id= :id")
    suspend fun getNoteByItsId(id:Int) :  Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

//    @Update
//    suspend fun upDateNote(note: Note) // not needed bcz the new version of the note is replaces in isert note function
}