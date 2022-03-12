package com.example.noteitallapp.note_feature.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteitallapp.R
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository
import com.example.noteitallapp.note_feature.presentation.addAndEditNote.AddEditNotesScreen
import com.example.noteitallapp.note_feature.presentation.notes.MakeNotesMainScreen
import com.example.noteitallapp.note_feature.presentation.notes.NotesViewModel
import com.example.noteitallapp.note_feature.presentation.utils.ScreenClass
import com.example.noteitallapp.ui.colorsAndTheme.NoteItallAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteItallAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController= rememberNavController()
                    NavHost(navController = navController, startDestination = ScreenClass.NotesScreen.route) {
                        composable(route = ScreenClass.NotesScreen.route) {
                            MakeNotesMainScreen(navController = navController)
                        }
                        composable(route=ScreenClass.AddEditNotesScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(navArgument(name = "noteId"){
                            type = NavType.IntType
                            defaultValue = -1
                        },
                            navArgument(name = "noteColor"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                            )) {
                            val noteColor = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNotesScreen(navController = navController, noteColor = noteColor)
                        }
                    }
                }
            }
        }
    }
}