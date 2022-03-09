package com.example.noteitallapp.note_feature.presentation.notes

import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.utils.NoteOrderBy

sealed class NotesEvent {

    data class OrderTheNotes(val noteOrderBy: NoteOrderBy) : NotesEvent()
    data class DeleteNoteOrder(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
