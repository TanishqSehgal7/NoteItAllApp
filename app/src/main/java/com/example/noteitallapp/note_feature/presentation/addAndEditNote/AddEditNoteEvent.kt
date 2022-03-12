package com.example.noteitallapp.note_feature.presentation.addAndEditNote

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title:String) : AddEditNoteEvent()
    data class ChangeFocusTitle(val focusState:FocusState) : AddEditNoteEvent()
    data class EnteredNoteContent(val content:String) : AddEditNoteEvent()
    data class ChangeFocusContent(val focusState:FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color:Int) : AddEditNoteEvent()
    object SaveNote:AddEditNoteEvent()

}