package com.unlck.demo.ui.detailalbum

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.unlck.demo.R
import com.unlck.demo.databinding.DetailAlbumFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import viewLifecycle

@AndroidEntryPoint
class DetailAlbumFragment : Fragment() {

    companion object {
        fun newInstance() = DetailAlbumFragment()
    }

    private val viewModel by viewModels<DetailAlbumViewModel>()
    private var _binding: DetailAlbumFragmentBinding? by viewLifecycle()
    private val binding
        get() = _binding ?: error("Called before onCreateView or after onDestroyView.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailAlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title = resources.getString(R.string.toolbar_title_detail_album, "")
        }
        viewModel.albumState.observe(viewLifecycleOwner) {
            it?.let { album ->
                binding.apply {
                    loader.visibility = View.GONE
                    albumContent.visibility = View.VISIBLE
                    Picasso.get().load(Uri.parse(album.url))
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(binding.albumImage, object : Callback {
                            override fun onSuccess() {
                                triggerChangeBackgroundColor()
                            }

                            override fun onError(e: Exception?) = Unit

                        })
                    albumId.text = resources.getString(R.string.album_id_text, album.albumId)
                    albumTitle.text = album.title
                    topAppBar.title =
                        resources.getString(R.string.toolbar_title_detail_album, "#${album.id}")
                }
            }
        }
    }

    private fun triggerChangeBackgroundColor() {
        (binding.albumImage.drawable as? BitmapDrawable?)?.bitmap?.let { bitmap ->
            Palette.from(
                bitmap
            ).generate { palette ->
                val dominantColor = palette?.getDominantColor(0xFFFFFF)
                dominantColor?.let { color ->
                    binding.scrollView.setBackgroundColor(
                        ColorUtils.setAlphaComponent(
                            color,
                            88
                        )
                    )
                }
            }
        }
    }
}