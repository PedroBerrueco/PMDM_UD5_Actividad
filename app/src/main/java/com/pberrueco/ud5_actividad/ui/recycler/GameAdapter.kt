package com.pberrueco.ud5_actividad.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pberrueco.ud5_actividad.R
import com.pberrueco.ud5_actividad.databinding.ItemGameBinding
import com.pberrueco.ud5_actividad.network.model.AllGamesResponseItem


class GameAdapter() : ListAdapter<AllGamesResponseItem, GameAdapter.ItemAdapterViewHolder>(
    GamesItemCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return ItemAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapterViewHolder, position: Int) {
        val gameItem = getItem(position)
        holder.binding.tvGameName.text = gameItem.internalName
        holder.binding.tvGameOriprice.text = gameItem.normalPrice
        holder.binding.tvGameRedprice.text = gameItem.salePrice
        Glide.with(holder.binding.root.context)
            .load(gameItem.thumb)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.imGamePhoto)
    }

    inner class ItemAdapterViewHolder (val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root)
}

    object GamesItemCallback: DiffUtil.ItemCallback<AllGamesResponseItem>(){
        override fun areItemsTheSame(oldItem: AllGamesResponseItem, newItem: AllGamesResponseItem): Boolean {
            return oldItem.gameID == newItem.gameID
        }

        override fun areContentsTheSame(oldItem: AllGamesResponseItem, newItem: AllGamesResponseItem): Boolean {
            return oldItem == newItem
        }

    }