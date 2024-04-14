package com.msmlabs.outcoding.presentation.shared.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

fun String.makeHyperlinkText() = buildAnnotatedString {
    val startIndex = 0
    val endIndex = this@makeHyperlinkText.length
    append(this@makeHyperlinkText)
    addStyle(
        style = SpanStyle(
            color = Color.Blue,
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
        ), start = startIndex, end = endIndex
    )
    addStringAnnotation(
        tag = "URL",
        annotation = this@makeHyperlinkText,
        start = startIndex,
        end = endIndex
    )
}

fun String.convertToHttpUrl() =
    if (this.startsWith("http://").not() && this.startsWith("https://").not())
        "http://$this"
    else this
