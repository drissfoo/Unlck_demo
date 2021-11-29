package com.unlck.demo.ui.listalbum

import android.view.View
import com.unlck.domain.model.Album
import com.unlck.domain.util.Result
import java.lang.ref.WeakReference

class ListAlbumView(private val weakFragment: WeakReference<ListAlbumFragment>) {

    fun updateView(result: Result<List<Album>>) {
        weakFragment.get()?.run {
            when (result) {
                is Result.Failure -> {
                    binding.apply {
                        swipeRefreshLayout.isRefreshing = false
                        albumProgressBar.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        albumRecyclerView.visibility = View.GONE
                    }
                }
                Result.Loading -> {
                    binding.apply {
                        swipeRefreshLayout.isRefreshing = true
                        albumProgressBar.visibility = View.VISIBLE
                        errorMessage.visibility = View.GONE
                        albumRecyclerView.visibility = View.GONE
                    }
                }
                is Result.Success -> {
                    if (!result.result.isNullOrEmpty()) {
                        binding.apply {
                            swipeRefreshLayout.isRefreshing = false
                            albumProgressBar.visibility = View.GONE
                            errorMessage.visibility = View.GONE
                            albumRecyclerView.visibility = View.VISIBLE
                            albumsAdapter?.updateAlbums(result.result)
                            recyclerViewVisible?.setIdleState(true)
                        }
                    } else Unit
                }
            }
        }
    }
}