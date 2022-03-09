package com.example.noteitallapp.note_feature.domain.use_cases

import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository
import com.example.noteitallapp.note_feature.domain.utils.NoteOrderBy
import com.example.noteitallapp.note_feature.domain.utils.OrderTypeForNotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesByDesiredOrder (private val notesRepository: NoteRepository) {

    operator fun invoke(noteOrder: NoteOrderBy=NoteOrderBy.OrderByDate(OrderTypeForNotes.ascendingOrder)) : Flow<List<Note>> {

        return notesRepository.getAllNotes().map { notes ->
           when(noteOrder.orderTypeForNotes) {
               is OrderTypeForNotes.ascendingOrder -> {
                   when(noteOrder) {
                       is NoteOrderBy.OrderByTitle -> notes.sortedBy { it.title }
                       is NoteOrderBy.OrderByDate -> notes.sortedBy { it.timeStamp }
                       is NoteOrderBy.OrderByColor -> notes.sortedBy { it.color }
                   }
               }
               is OrderTypeForNotes.descendingOrder -> {
                   when(noteOrder) {
                       is NoteOrderBy.OrderByTitle -> notes.sortedByDescending { it.title }
                       is NoteOrderBy.OrderByDate -> notes.sortedByDescending { it.timeStamp }
                       is NoteOrderBy.OrderByColor -> notes.sortedByDescending { it.color }
                   }
               }
           }
        }
    }
}