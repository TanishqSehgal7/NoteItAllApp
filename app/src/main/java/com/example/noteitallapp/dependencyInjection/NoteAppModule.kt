package com.example.noteitallapp.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.noteitallapp.note_feature.data.dataSource.NoteDatabase
import com.example.noteitallapp.note_feature.data.repository.NoteRepositoryForDatabase
import com.example.noteitallapp.note_feature.domain.repository.NoteRepository
import com.example.noteitallapp.note_feature.domain.use_cases.AddNote
import com.example.noteitallapp.note_feature.domain.use_cases.DeleteNoteUseCase
import com.example.noteitallapp.note_feature.domain.use_cases.GetNotesByDesiredOrder
import com.example.noteitallapp.note_feature.domain.use_cases.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteAppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app:Application) : NoteDatabase {
        return Room.databaseBuilder(app,NoteDatabase::class.java,NoteDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providesNotesRepository(noteDatabase: NoteDatabase) : NoteRepository {
        return NoteRepositoryForDatabase(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(notesRepository: NoteRepository) : NotesUseCases {
        return NotesUseCases(getNotesUseCase = GetNotesByDesiredOrder(notesRepository), deleteNoteUseCase = DeleteNoteUseCase(notesRepository), addNote = AddNote(notesRepository))
    }

}