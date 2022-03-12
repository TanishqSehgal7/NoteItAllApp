package com.example.noteitallapp.note_feature.presentation.utils

sealed class ScreenClass(val route:String) {
    object NotesScreen : ScreenClass("notes_screen")
    object AddEditNotesScreen :ScreenClass("add_edit_notes_screen")
}