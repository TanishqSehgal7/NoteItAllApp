package com.example.noteitallapp.note_feature.domain.utils

import android.icu.text.CaseMap

sealed class NoteOrderBy(val orderTypeForNotes: OrderTypeForNotes) {

    class OrderByTitle(orderTypeForNotes: OrderTypeForNotes) : NoteOrderBy(orderTypeForNotes)
    class OrderByDate(orderTypeForNotes: OrderTypeForNotes) : NoteOrderBy(orderTypeForNotes)
    class OrderByColor(orderTypeForNotes: OrderTypeForNotes) : NoteOrderBy(orderTypeForNotes)

    fun copyOrderTypeForNotes(orderTypeForNotes: OrderTypeForNotes) : NoteOrderBy {
        return when(this) {
            is OrderByTitle -> OrderByTitle(orderTypeForNotes)
            is OrderByDate -> OrderByDate(orderTypeForNotes)
            is OrderByColor -> OrderByColor(orderTypeForNotes)
        }
    }

}