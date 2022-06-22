package com.example.pokedex.presentation.details

import androidx.annotation.DrawableRes
import com.example.pokedex.R

object IconTypePicker {
    @DrawableRes
    fun getIcon(type: String?): Int {
        return when (type) {
            "fighting" -> R.drawable.ic_fighting
            "flying" -> R.drawable.ic_flying
            "poison" -> R.drawable.ic_poison
            "ground" -> R.drawable.ic_ground
            "rock" -> R.drawable.ic_rock
            "bug" -> R.drawable.ic_bug
            "ghost" -> R.drawable.ic_ghost
            "steel" -> R.drawable.ic_steel
            "fire" -> R.drawable.ic_fire
            "water" -> R.drawable.ic_water
            "grass" -> R.drawable.ic_grass
            "electric" -> R.drawable.ic_electric
            "psychic" -> R.drawable.ic_psychic
            "ice" -> R.drawable.ic_ice
            "dragon" -> R.drawable.ic_dragon
            "fairy" -> R.drawable.ic_fairy
            "dark" -> R.drawable.ic_dark
            else -> R.drawable.ic_normal
        }
    }
}