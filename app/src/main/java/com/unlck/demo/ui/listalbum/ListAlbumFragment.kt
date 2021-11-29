package com.unlck.demo.ui.listalbum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.unlck.demo.R
import com.unlck.demo.databinding.ListAlbumFragmentBinding
import com.unlck.domain.model.Album
import com.unlck.domain.util.Result
import com.unlck.demo.ui.detailalbum.KEY_ALBUM_DATA
import com.unlck.demo.util.SimpleIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import viewLifecycle
import java.lang.ref.WeakReference

@AndroidEntryPoint
class ListAlbumFragment : Fragment() {

    companion object {
        fun newInstance() = ListAlbumFragment()
    }

    private var listAlbumView: ListAlbumView? = null
    internal var albumsAdapter: AlbumsAdapter? = null
    private val viewModel by viewModels<ListAlbumViewModel>()
    private var _binding: ListAlbumFragmentBinding? by viewLifecycle()
    internal val binding
        get() = _binding ?: error("Called before onCreateView or after onDestroyView.")

    @VisibleForTesting
    var recyclerViewVisible: SimpleIdlingResource? = null
        get() {
            if (field == null) {
                field = SimpleIdlingResource()
            }
            return field
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListAlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSwipeToRefresh()
        viewModel.albumsResult.observe(viewLifecycleOwner) { result: Result<List<Album>> ->
            listAlbumView?.updateView(result)
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel)
    }

    private fun initRecyclerView() {
        recyclerViewVisible?.setIdleState(false)
        listAlbumView = ListAlbumView(WeakReference(this))
        binding.albumRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.albumRecyclerView.setHasFixedSize(true)
        albumsAdapter = AlbumsAdapter(listOf()) { album: Album ->
            val bundle = bundleOf(KEY_ALBUM_DATA to album)
            findNavController().navigate(
                R.id.action_listAlbumFragment_to_detailAlbumFragment,
                bundle
            )
        }
        binding.albumRecyclerView.adapter = albumsAdapter
    }

}