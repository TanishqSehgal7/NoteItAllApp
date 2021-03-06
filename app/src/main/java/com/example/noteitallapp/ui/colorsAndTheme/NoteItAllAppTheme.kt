package com.example.noteitallapp.ui.colorsAndTheme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(primary = Color.White,
    background = Color.DarkGray, onBackground = Color.White, surface = myTeal, onSurface = myGrey)

@Composable
fun NoteItallAppTheme(darkTheme:Boolean=true, content:@Composable() () -> Unit) {
    MaterialTheme(colors = DarkColorPalette, typography = Typography, shapes = Shapes, content = content)
}