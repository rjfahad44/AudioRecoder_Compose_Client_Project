package com.ft.audiorecoder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ft.audiorecoder.MainContent
import com.ft.audiorecoder.services.AudioService
import com.ft.audiorecoder.ui.composables.CustomDialog
import com.ft.audiorecoder.viewModels.RecordingsViewModel
import com.ft.audiorecoder.viewModels.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: TimerViewModel,
    recordingViewModel: RecordingsViewModel,
    mService: AudioService?,
) {
    val showDialog = rememberSaveable { mutableStateOf(false) }

    if (showDialog.value)
        CustomDialog(
            navController = navController,
            value = "Recording_${System.currentTimeMillis()}",
            setShowDialog = {
                showDialog.value = it
            },
            recordingViewModel = recordingViewModel,
            timerViewModel = viewModel,
            mService = mService,
        ) {
        }

    Surface {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .clip(RoundedCornerShape(16.dp)),
        ) {
            NavHost(navController = navController, startDestination = "main_screen") {
                composable("main_screen") {
                    MainContent(
                        viewModel = viewModel,
                        onRecord = {
                            val onPause = mService?.isPaused ?: false
                            val recording = mService?.isRecordingStarted ?: false
                            when {
                                onPause -> {
                                    mService?.resumeRecorder()
                                    viewModel.toggleTimer(false)
                                }
                                recording -> {
                                    mService?.pauseRecorder()
                                    viewModel.toggleTimer(true)
                                }
                                else -> {
                                    mService?.startRecording()
                                    viewModel.startTimer()
                                }
                            }
                        },
                        onStop = {
                            viewModel.stopTimer()
                            mService?.stopRecording()
                            mService?.stopRecorderService()
                        }
                    ) {// done or menu button
                        if (mService?.isRecordingStarted == true) {
                            showDialog.value = true
                        } else {
                            navController.navigate("setting_screen")
                        }
                    }
                }
                composable("setting_screen") {
                    RecordingListScreen(navController, recordingViewModel)
                }
            }
        }
    }
}