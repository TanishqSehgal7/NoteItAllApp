package com.example.noteitallapp.note_feature.presentation.addAndEditNote.AddEditNoteViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteitallapp.note_feature.domain.model.InvalidNoteException
import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.use_cases.NotesUseCases
import com.example.noteitallapp.note_feature.presentation.addAndEditNote.AddEditNoteEvent
import com.example.noteitallapp.note_feature.presentation.addAndEditNote.NoteTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor (private val notesUseCases: NotesUseCases,savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter Title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter your note here..."))
    val noteContent: State<NoteTextFieldState> = _noteTitle

    private val _noteColor = mutableStateOf<Int>(Note.colorsForNotes.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentIdOfTheNote: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId->
            if (noteId!=-1) {
                viewModelScope.launch {
                    notesUseCases.getNoteById(noteId)?.also {
                        currentIdOfTheNote=it.id
                        _noteTitle.value = noteTitle.value.copy(text = it.title, ishintVisible = false)
                        _noteContent.value = noteContent.value.copy(text = it.content, ishintVisible = false)
                        _noteColor.value = it.color
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(text = event.title)
            }
            is AddEditNoteEvent.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(ishintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank())
            }
            is AddEditNoteEvent.EnteredNoteContent -> {
                _noteContent.value = noteContent.value.copy(text = event.content)
            }
            is AddEditNoteEvent.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(ishintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank())
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(Note(title = noteTitle.value.text,
                            content = noteContent.value.text,
                            timeStamp = System.currentTimeMillis(), color = noteColor.value, id = currentIdOfTheNote))
                            _eventFlow.emit(UiEvent.savedNote)
                    } catch (e:InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message?:"Could'nt Save Note!"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message:String):UiEvent()
        object savedNote:UiEvent()
    }

}