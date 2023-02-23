package com.example.kitsuanimeapp.ui.view.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(top = 8.dp, bottom = 1.dp),
    )
}

@Composable
fun SubTitleText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(bottom = 16.dp, top = 1.dp),
    )
}

@Composable
fun BodyText(text: String, onSelected: () -> Unit) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 6,
        modifier = Modifier.clickable { onSelected() },
        softWrap = true,
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
