package com.example.noteitallapp.note_feature.domain.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteitallapp.ui.colorsAndTheme.*
import java.lang.Exception

@Entity
data class Note (
    @PrimaryKey val id: Int?=null,
    val title:String,
    val content:String,
    val color:Int,
    val timeStamp:Long) {

    companion object {
        val colorsForNotes = listOf<androidx.compose.ui.graphics.Color>(myOrange, myYellow,
            myCrimson, myCyan, myLime, myPink, myViolet, myTeal)
    }

}

class InvalidNoteException(message:String) :Exception(message)