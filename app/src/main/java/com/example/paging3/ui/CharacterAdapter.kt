package com.example.paging3.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paging3.databinding.ItemCharacterBinding
import com.example.paging3.model.Character

class CharacterAdapter :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.apply {
                characterName.text = character.name
                characterStatus.text = "${character.status} - ${character.species}"
                characterLocation.text = character.location?.name ?: "Unknown location"
                characterLocation.text = character.location?.name ?: "Unknown"

                when (character.status?.lowercase()) {
                    "alive" -> statusIndicator.setBackgroundColor(Color.GREEN)
                    "dead" -> statusIndicator.setBackgroundColor(Color.RED)
                    "unknown" -> statusIndicator.setBackgroundColor(Color.GRAY)
                    else -> statusIndicator.setBackgroundColor(Color.DKGRAY)
                }
                Glide.with(characterImage.context)
                    .load(character.image)
                    .into(characterImage)
            }
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}