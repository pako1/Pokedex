package com.example.pokedex.presentation.pokemons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.IntegerRes
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.domain.models.Pokemon

class PokemonsAdapter(
    val onItemClick: (Pair<Pokemon, View>) -> Unit,
    val onLikeClick: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonsAdapter.PokemonViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        setTransitionName(holder.itemView, pokemon.name)
        holder.bindTo(pokemon)
    }

    inner class PokemonViewHolder(private val pokemonBinding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(pokemonBinding.root) {

        fun bindTo(pokemon: Pokemon) = with(pokemonBinding) {
            cardViewStart.setOnClickListener {
                onItemClick.invoke(pokemon to itemView)
            }
            image.load(pokemon.artwork) {
                crossfade(500)
            }
            heartButton.setImageResource(getLikeIcon(pokemon.isFavorite))

            heartButton.setOnClickListener {
                onLikeClick.invoke(pokemon)
                (it as ImageButton).animateLike()
                heartButton.setImageResource(getLikeIcon(!pokemon.isFavorite))
            }
            pokemonIndex.text = pokemon.id
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            name.text = pokemon.name
        }
    }

}

@IntegerRes
fun getLikeIcon(isFavorite: Boolean): Int {
    return if (isFavorite) R.drawable.ic_heart_liked else R.drawable.ic_heart_unliked
}

fun ImageButton.animateLike() {
    val scaleAnimation = ScaleAnimation(
        0.7f,
        1.0f,
        0.7f,
        1.0f,
        Animation.RELATIVE_TO_SELF,
        0.7f,
        Animation.RELATIVE_TO_SELF,
        0.7f
    ).apply {
        duration = 500
        interpolator = BounceInterpolator()
    }
    startAnimation(scaleAnimation)
}

private class DiffCallback : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
}