package com.example.paging3.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.paging3.R
import com.example.paging3.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        val characterId = arguments?.getInt("CHARACTER_ID") ?: 0
        if (characterId != 0) {
            viewModel.fetchCharacterDetails(characterId)
        }

        viewModel.characterDetails.observe(viewLifecycleOwner) { character ->
            binding.apply {
                characterName.text = character?.name
                characterStatus.text = "${character?.status} - ${character?.species}"
                Glide.with(characterImage.context)
                    .load(character?.image)
                    .placeholder(R.drawable.morty)
                    .transform(RoundedCorners(14))
                    .into(characterImage)
                tvLastLocation.text = character?.location?.name ?: "Unknown"
                tvSpesies.text = character?.species?.trim() ?: "Unknown"
                tvGender.text = character?.gender?.trim() ?: "Unknown"

                when (character?.status?.lowercase()) {
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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}