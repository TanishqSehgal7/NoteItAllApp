package com.example.noteitallapp.note_feature.domain.use_cases

import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository

class GetNotesByIdUseCase(private val notesRepository: NoteRepository){

    suspend operator fun invoke(id:Int) : Note? {
       return notesRepository.getNoteById(id)
    }

}