package com.example.noteitallapp.note_feature.domain.utils

sealed class OrderTypeForNotes {
    object ascendingOrder : OrderTypeForNotes()
    object descendingOrder : OrderTypeForNotes()
}
