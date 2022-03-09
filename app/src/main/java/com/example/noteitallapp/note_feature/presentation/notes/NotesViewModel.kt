package com.example.noteitallapp.note_feature.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.domain.use_cases.DeleteNoteUseCase
import com.example.noteitallapp.note_feature.domain.use_cases.NotesUseCases
import com.example.noteitallapp.note_feature.domain.utils.NoteOrderBy
import com.example.noteitallapp.note_feature.domain.utils.OrderTypeForNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesUseCases: NotesUseCases) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
    var job : Job? = null
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(NoteOrderBy.OrderByDate(OrderTypeForNotes.ascendingOrder))
    }

    fun onEvent(notesEvent: NotesEvent) {
        when(notesEvent) {

            is NotesEvent.OrderTheNotes-> {
                if (state.value.noteOrderBy::class==notesEvent.noteOrderBy::class &&
                        state.value.noteOrderBy.orderTypeForNotes == notesEvent.noteOrderBy.orderTypeForNotes) {
                    return
                }
                getNotes(notesEvent.noteOrderBy)
            }

            is NotesEvent.DeleteNoteOrder -> {
             job= viewModelScope.launch {
                 notesUseCases.deleteNoteUseCase(notesEvent.note)
                 recentlyDeletedNote = notesEvent.note
             }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(recentlyDeletedNote?:return@launch)
                    recentlyDeletedNote=null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrderBy: NoteOrderBy) {
        job?.cancel()
        job= notesUseCases.getNotesUseCase(noteOrderBy).onEach { notes->
            _state.value = state.value.copy(notes = notes, noteOrderBy = noteOrderBy,false)
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}