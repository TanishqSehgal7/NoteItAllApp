package com.example.noteitallapp.note_feature.domain.use_cases

import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository

class DeleteNoteUseCase(val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}