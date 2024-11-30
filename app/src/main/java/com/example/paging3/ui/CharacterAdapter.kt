package com.example.paging3.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.paging3.databinding.ItemCharacterBinding
import com.example.paging3.model.Character

class CharacterAdapter(private val onItemClicked: (Int) -> Unit) :
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

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.apply {
                characterName.text = character.name
                characterStatus.text = "${character.status} - ${character.species}"
                characterLocation.text = character.location?.name ?: "Unknown location"
                Glide.with(characterImage.context)
                    .load(character.image)
                    .transform(RoundedCorners(14))
                    .into(characterImage)

                root.setOnClickListener {
                    onItemClicked(character.id ?: 0)
                }

                when (character.status?.lowercase()) {
                    "alive" -> {
                        val drawable = GradientDrawable().apply {
                            shape = GradientDrawable.OVAL
                            setColor(Color.GREEN)
                        }
                        statusIndicator.background = drawable
                    }
                    "dead" -> {
                        val drawable = GradientDrawable().apply {
                            shape = GradientDrawable.OVAL
                            setColor(Color.RED)
                        }
                        statusIndicator.background = drawable
                    }
                    "unknown" -> {
                        val drawable = GradientDrawable().apply {
                            shape = GradientDrawable.OVAL
                            setColor(Color.GRAY)
                        }
                        statusIndicator.background = drawable
                    }
                    else -> {
                        val drawable = GradientDrawable().apply {
                            shape = GradientDrawable.OVAL
                            setColor(Color.DKGRAY)
                        }
                        statusIndicator.background = drawable
                    }
                }
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