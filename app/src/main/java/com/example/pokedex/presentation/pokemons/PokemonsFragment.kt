package com.example.pokedex.presentation.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonsBinding
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.Constants.POKEMON_ID_KEY
import com.example.pokedex.utils.Constants.POKEMON_NAME_KEY
import com.example.pokedex.utils.collectSafelyOneFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonsBinding

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: PokemonsViewModel by viewModels()
    private val navController by lazy { findNavController() }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonsAdapter by lazy {
        PokemonsAdapter(
            onItemClick = ::goToPokemonDetails,
            onLikeClick = ::favouritePokemon
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsBinding.inflate(layoutInflater, container, false)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.pokemonRecycler setupWith linearLayoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.pokemonsErrorState.retryButton.setOnClickListener {
            viewModel.retry()
        }
        collectSafelyOneFlow(viewModel.pokemons) { state ->
            renderUiState(state)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun renderUiState(state: PokemonsState<List<Pokemon>>) {
        when (state) {
            is PokemonsState.Loading -> with(binding) {
                pokemonRecycler.visibility = View.GONE
                pokemonProgressBar.visibility = View.VISIBLE
                pokemonsErrorState.group.visibility = View.GONE
            }
            is PokemonsState.Success -> with(binding) {
                pokemonsErrorState.group.visibility = View.GONE
                pokemonsAdapter.submitList(state.data)
                pokemonRecycler.visibility = View.VISIBLE
                pokemonRecycler.doOnPreDraw { startPostponedEnterTransition() }
                pokemonProgressBar.visibility = View.GONE
            }
            is PokemonsState.Failure -> with(binding) {
                pokemonsErrorState.group.visibility = View.VISIBLE
                pokemonProgressBar.visibility = View.GONE
            }
        }
    }

    private infix fun RecyclerView.setupWith(layoutRecManager: RecyclerView.LayoutManager) {
        adapter = pokemonsAdapter
        layoutManager = layoutRecManager
    }

    private fun favouritePokemon(pokemon: Pokemon) {
        viewModel.updateFavorite(pokemon)
    }

    private fun goToPokemonDetails(pair: Pair<Pokemon, View>) {
        val pokemon = pair.first
        val view = pair.second
        val extras = FragmentNavigatorExtras(view to (pokemon.name))
        navController.navigate(
            resId = R.id.detailFragment,
            args = bundleOf(POKEMON_ID_KEY to pokemon.id, POKEMON_NAME_KEY to pokemon.name),
            navigatorExtras = extras,
            navOptions = null
        )
    }
}