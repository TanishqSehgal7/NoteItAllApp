package com.example.noteitallapp.note_feature.presentation.notes

import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.utils.NoteOrderBy
import com.example.noteitallapp.note_feature.domain.utils.OrderTypeForNotes

data class NotesState (
    val notes:List<Note> = emptyList(),
    val noteOrderBy: NoteOrderBy=NoteOrderBy.OrderByDate(OrderTypeForNotes.ascendingOrder),
    val isOrderSectionVisible:Boolean=false)