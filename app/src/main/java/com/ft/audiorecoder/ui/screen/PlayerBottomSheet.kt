package com.ft.audiorecoder.ui.screen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ft.audiorecoder.ui.composables.PlayerView
import com.ft.audiorecoder.ui.composables.RecordItem
import com.ft.audiorecoder.ui.composables.Search
import com.ft.audiorecoder.viewModels.RecordingsViewModel
import kotlinx.coroutines.launch

private fun play(path: String, mediaPlayer: MediaPlayer) {
    try {
        mediaPlayer.apply {
            setOnPreparedListener { mp -> mp.start() }
            setDataSource(path)
            prepareAsync()
        }
    } catch (e: Exception) {
        Log.d("TAG", "play: # ${e.message}")
        e.printStackTrace()
    }
}

@ExperimentalMaterial3Api
@Composable
fun PlayerBottomSheet(
    viewModel: RecordingsViewModel,
    navController: NavController,
) {
    val editTextState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Log.d("TAG", "PlayerBottomSheet: ${editTextState.value.text}")
    val recordings =
        viewModel.getRecordData(editTextState.value.text).collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState{
            it == SheetValue.Hidden
        }
    )
    var mediaPlayer: MediaPlayer? by remember {
        mutableStateOf(null)
    }
    var path by remember {
        mutableStateOf("")
    }
    var fileName by remember {
        mutableStateOf("")
    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.Black)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = fileName,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.fillMaxWidth(0.90f)
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = Color.LightGray,
                            contentDescription = "close",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    mediaPlayer?.stop()
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    coroutineScope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.hide()
                                    }
                                }
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PlayerView(
                            getSessionId = { mediaPlayer?.audioSessionId },
                            onPlay = {
                                if (mediaPlayer == null) {
                                    mediaPlayer = MediaPlayer()
                                    play(mediaPlayer = mediaPlayer!!, path = path)
                                } else {
                                    mediaPlayer?.start()
                                }
                            }
                        ) {
                            mediaPlayer?.pause()
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp,
        content = {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(it)
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 16.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Search(
                            placeHolderMsg = "Search recording",
                            state = editTextState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        )
                    }
                    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
                        items(items = recordings.value) { record ->
                            RecordItem(record) {
                                path = record.filePath
                                fileName = record.filename
                                coroutineScope.launch {
                                    if (bottomSheetScaffoldState.bottomSheetState.isVisible) {
                                        bottomSheetScaffoldState.bottomSheetState.hide()
                                    } else {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
}