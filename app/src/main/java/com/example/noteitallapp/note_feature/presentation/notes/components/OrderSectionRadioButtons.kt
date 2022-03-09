package com.example.noteitallapp.note_feature.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteitallapp.note_feature.domain.utils.NoteOrderBy
import com.example.noteitallapp.note_feature.domain.utils.OrderTypeForNotes

@Composable
fun OrderSection(modifier: Modifier=Modifier,noteOrderBy: NoteOrderBy=NoteOrderBy.OrderByDate(OrderTypeForNotes.ascendingOrder),orderChange:(NoteOrderBy)->Unit) {
    
    Column(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth()) { 
            // row 1 with 3 radio buttons
            DefaultRadioButton(text = "Title" ,
                selected = noteOrderBy is NoteOrderBy.OrderByTitle ,
                onChecked = { orderChange(NoteOrderBy.OrderByTitle(noteOrderBy.orderTypeForNotes)) })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(text = "Date" ,
                selected = noteOrderBy is NoteOrderBy.OrderByDate ,
                onChecked = { orderChange(NoteOrderBy.OrderByDate(noteOrderBy.orderTypeForNotes)) })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(text = "Color" ,
                selected = noteOrderBy is NoteOrderBy.OrderByColor ,
                onChecked = { orderChange(NoteOrderBy.OrderByColor(noteOrderBy.orderTypeForNotes)) })
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(text = "Ascending" ,
                selected = noteOrderBy.orderTypeForNotes is OrderTypeForNotes.ascendingOrder,
                onChecked = { orderChange(noteOrderBy.copyOrderTypeForNotes(OrderTypeForNotes.ascendingOrder)) })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(text = "Descending" ,
                selected = noteOrderBy.orderTypeForNotes is OrderTypeForNotes.descendingOrder,
                onChecked = { orderChange(noteOrderBy.copyOrderTypeForNotes(OrderTypeForNotes.descendingOrder)) })
            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}