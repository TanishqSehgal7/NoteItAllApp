package com.example.noteitallapp.note_feature.domain.use_cases

import androidx.compose.ui.window.Dialog
import com.example.noteitallapp.note_feature.domain.model.InvalidNoteException
import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository

class AddNote (private val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The Title of the note cannot be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note cannot be empty.")
        }
        noteRepository.insertNote(note)
    }

}