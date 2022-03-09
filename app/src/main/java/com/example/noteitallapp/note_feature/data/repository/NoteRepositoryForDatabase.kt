package com.example.noteitallapp.note_feature.data.repository

import com.example.noteitallapp.note_feature.data.dataSource.notedao
import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryForDatabase(private val notedao: notedao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return notedao.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notedao.getNoteByItsId(id)
    }

    override suspend fun insertNote(note: Note) {
        return notedao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return notedao.deleteNote(note)
    }

}