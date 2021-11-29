package com.unlck.demo.ui.listalbum

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.unlck.domain.model.Album
import com.unlck.domain.usecases.GetListAlbumsUseCase
import com.unlck.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAlbumViewModel @Inject constructor(val getListAlbumsUseCase: GetListAlbumsUseCase) :
    ViewModel(), SwipeRefreshLayout.OnRefreshListener {

    private val _albumsResult = MutableLiveData<Result<List<Album>>>(Result.Loading)
    val albumsResult: LiveData<Result<List<Album>>>
        get() = _albumsResult

    init {
        refresh()
    }

    @VisibleForTesting
    fun refresh(force: Boolean = false) {
        viewModelScope.launch {
            getListAlbumsUseCase(force)
                .onStart {
                    _albumsResult.value = Result.Loading
                }
                .catch { e ->
                    _albumsResult.value = Result.Failure(e)
                }
                .collect { response: Result<List<Album>> ->
                    _albumsResult.value = response
                }
        }
    }

    override fun onRefresh() {
        refresh(true)
    }
}