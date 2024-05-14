package com.ft.audiorecoder.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ft.audiorecoder.R

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    buttonSize: Dp = 55.dp,
    iconSize: Float = 1f,
    color: Color = Color.Gray.copy(alpha = 0.6f),
    onClick: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(iconId),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = color, shape = CircleShape)
                .size(buttonSize)
                .scale(iconSize)
                .clickable {
                    onClick()
                },
        )
    }
}

@Preview
@Composable
fun PreviewOFButton() {
    RoundButton(
        iconId = R.drawable.ic_play,
        buttonSize = 55.dp,
        iconSize = 1f,
        color = Color.Gray.copy(alpha = 0.6f),
    ) {
    }
}