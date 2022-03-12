package com.example.noteitallapp.note_feature.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.noteitallapp.note_feature.domain.model.Note

@Composable
fun MakeNoteItemForDisplay(note: Note, modifier: Modifier=Modifier, cornerRadius: Dp=10.dp, cutCornerRadius:Dp =10.dp, onDeleteClick:()->Unit) {

    Box(modifier = Modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width-cutCornerRadius.toPx(),0f)
                lineTo(size.width,cutCornerRadius.toPx())
                lineTo(size.width,size.height)
                lineTo(0f,size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

                // rectangle for the flap on the card ... its color is blended with black
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(note.color,0x000000,0.4f)),
                    topLeft = Offset(size.width-cutCornerRadius.toPx(),-100f),
                    size = Size(cutCornerRadius.toPx()+120f,cutCornerRadius.toPx()+120f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

            }
        }

        // textViews and delete button
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(end = 32.dp)) {
            // textView for note title
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // TextView for note content
            Text(text = note.content.substring(0,(note.content.length)/2)+"....",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines=5,
                overflow = TextOverflow.Ellipsis
            )
        }

        // delete note icon
        IconButton(onClick = onDeleteClick, modifier = Modifier.align(Alignment.BottomEnd)) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note")
        }
    }
}