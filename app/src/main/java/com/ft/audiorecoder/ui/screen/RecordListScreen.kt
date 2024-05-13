package com.ft.audiorecoder.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ft.audiorecoder.ui.theme.AudioRecoderTheme
import com.ft.audiorecoder.viewModels.RecordingsViewModel

@ExperimentalMaterial3Api
@Composable
fun RecordingListScreen(
    navController: NavController,
    viewModel: RecordingsViewModel,
) {
    PlayerBottomSheet(
        viewModel = viewModel,
        navController = navController
    )
}


@Preview
@Composable
fun RecordListPreview() {
    AudioRecoderTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}


