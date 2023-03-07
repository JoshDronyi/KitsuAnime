package com.example.kitsuanimeapp.ui.view.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Visible,
        maxLines = 3,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp),
    )
}

@Composable
fun SubTitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun BodyText(text: String, onSelected: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 8,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun NSFWTag() {
    val rounding = RoundedCornerShape(24.dp)
    Text(
        text = "NSFW",
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .background(Color.Red.copy(alpha = .2f), rounding)
            .border(2.dp, Color.Red, rounding)
            .padding(16.dp)
            .clip(rounding),
        color = Color.Red,
    )
}

@Composable
fun MediaCount(modifier: Modifier = Modifier, count: Int = 0) {
    val rounding = RoundedCornerShape(32.dp)
    Text(
        text = count.toString(),
        color = Color.White,
        modifier = modifier
            .wrapContentSize()
            .background(Color.DarkGray, rounding)
            .border(2.dp, Color.White, rounding)
            .padding(4.dp),
    )
}
