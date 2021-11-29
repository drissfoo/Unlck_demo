package com.unlck.demo.ui.detailalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.unlck.domain.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val KEY_ALBUM_DATA = "key_album_data"

@HiltViewModel
class DetailAlbumViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    val albumState: LiveData<Album> = savedStateHandle.getLiveData(KEY_ALBUM_DATA)
}