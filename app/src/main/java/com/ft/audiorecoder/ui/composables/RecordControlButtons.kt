package com.ft.audiorecoder.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ft.audiorecoder.R
import com.ft.audiorecoder.ui.theme.AudioRecoderTheme

@Composable
fun RecordControlButtons(
    onRecord: () -> Unit = {},
    onStop: () -> Unit = {},
    onToggleRecord: () -> Unit = {},
) {
    var isPressedTogglePause by rememberSaveable {
        mutableStateOf(false)
    }

    var state by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(corner = CornerSize(8.dp))
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RoundButton(
            modifier = Modifier.weight(1f),
            iconId = R.drawable.ic_close,
            buttonSize = 55.dp,
            iconSize = 1.5f,
            color = Color.Gray.copy(alpha = 0.6f)
        ) {
            onStop()
            state = true
        }
        Spacer(modifier = Modifier.height(8.dp))
        RoundButton(
            modifier = Modifier.weight(1f),
            iconId = if (isPressedTogglePause) {
                R.drawable.ic_pause
            } else {
                R.drawable.ic_play
            },
            buttonSize = 80.dp,
            iconSize = 1.5f,
            color = Color.Green.copy(alpha = 0.6f)
        ) {
            onRecord()
            isPressedTogglePause = !isPressedTogglePause
            state = true
        }
        Spacer(modifier = Modifier.height(8.dp))
        RoundButton(
            modifier = Modifier.weight(1f),
            iconId = if (state) {
                R.drawable.ic_done
            } else {
                R.drawable.ic_menu
            },
            buttonSize = 55.dp,
            iconSize = 1.5f,
            color = Color.Gray.copy(alpha = 0.6f),
        ) {
            onToggleRecord()
            state = false
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ControlButtonPreview() {
    AudioRecoderTheme {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            RecordControlButtons()
        }
    }
}