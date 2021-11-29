package com.unlck.demo.ui.listalbum

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unlck.demo.R
import com.unlck.demo.databinding.AlbumItemBinding
import com.unlck.domain.model.Album

class AlbumsAdapter(albums: List<Album>, val callback: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var items = mutableListOf<Album>()

    init {
        items.addAll(albums)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            AlbumItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbums(newAlbums: List<Album>) {
        items.clear()
        items.addAll(newAlbums)
        notifyDataSetChanged()
    }

    inner class AlbumViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                callback(items[adapterPosition])
            }
        }

        fun bind(album: Album) {
            binding.albumTitle.text = album.title
            binding.albumId.text =
                itemView.resources.getString(R.string.album_id_short_text, album.id)
            Picasso.get()
                .load(Uri.parse(album.thumbnailUrl))
                .placeholder(R.drawable.placeholder_thumb).error(R.drawable.placeholder_thumb)
                .into(binding.albumImage)
        }
    }
}
