package com.example.noteitallapp.note_feature.presentation.addAndEditNote

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteitallapp.note_feature.domain.model.Note
import com.example.noteitallapp.note_feature.presentation.addAndEditNote.AddEditNoteViewModel.AddEditNoteViewModel
import com.example.noteitallapp.note_feature.presentation.addAndEditNote.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNotesScreen(navController: NavController, noteColor:Int, viewModel:AddEditNoteViewModel = hiltViewModel()) {

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()

    val noteBgAnimatable = remember {
        Animatable(Color(if (noteColor!=-1) noteColor else viewModel.noteColor.value))
    }
    val coroutineScopeForBgAnimation = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackBar-> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditNoteViewModel.UiEvent.savedNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = { FloatingActionButton(
        onClick = {
            viewModel.onEvent(AddEditNoteEvent.SaveNote)
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
     }
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(noteBgAnimatable.value)
            .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Note.colorsForNotes.forEach { color->
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp, color = if (viewModel.noteColor.value == colorInt) {
                                Color.Black
                            } else Color.Transparent, shape = CircleShape
                        )
                        .clickable {
                            coroutineScopeForBgAnimation.launch {
                                noteBgAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                            viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                        })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                modifier = Modifier,
                onValueChange = {
                   viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                } ,
                textStyle = MaterialTheme.typography.h5,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeFocusTitle(it))
                },
                isHintVisible = titleState.ishintVisible, singleLine = true)

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                modifier = Modifier.fillMaxHeight(),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredNoteContent(it))
                } ,
                textStyle = MaterialTheme.typography.h5,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeFocusContent(it))
                },
                isHintVisible = titleState.ishintVisible)

        }
    }
}