package com.example.pokedex.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setTransitionName
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailBinding
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.Stat
import com.example.pokedex.utils.Constants.ATTACK
import com.example.pokedex.utils.Constants.DEFENSE
import com.example.pokedex.utils.Constants.HP
import com.example.pokedex.utils.Constants.POKEMON_NAME_KEY
import com.example.pokedex.utils.Constants.SINGLE_TYPE_POKEMON
import com.example.pokedex.utils.Constants.SPEED
import com.example.pokedex.utils.collectSafelyOneFlow
import com.example.pokedex.utils.reach
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.content.visibility = View.INVISIBLE
        collectSafelyOneFlow(viewModel.pokemonDetails) { result -> renderUiState(result) }
        return binding.root
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun renderUiState(result: DetailState<PokemonDetail>) {
        when (result) {
            is DetailState.Loading -> with(binding) {
                content.visibility = View.GONE
                errorState.group.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is DetailState.Success -> {
                with(binding) {
                    cardViewEnd.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                    )
                    content.visibility = View.VISIBLE
                    image.load(result.data.artwork) {
                        crossfade(600)
                    }
                    animateStatsBars(result.data.stats)
                    val pokemonIndex = "#${result.data.id}"
                    order.text = pokemonIndex
                    val typeSize = result.data.types?.size
                    if (typeSize == SINGLE_TYPE_POKEMON) {
                        displaySingleTypePokemon(result)
                    } else {
                        displayTwoTypePokemonFrom(result)
                    }
                    heightValue.text = result.data.height.toString()
                    weightValue.text = result.data.weight.toString()
                    name.text = result.data.name
                    progressBar.visibility = View.GONE
                }
            }
            is DetailState.Failure -> with(binding) {
                errorState.group.visibility = View.VISIBLE
                content.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun FragmentDetailBinding.displaySingleTypePokemon(result: DetailState.Success<PokemonDetail>) {
        val firstTypeName = result.data.types?.get(0)?.typeName
        firstType.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                ColorTypePicker.getTypeColor(firstTypeName)
            )
        )
        firstIconType.setImageResource(IconTypePicker.getIcon(firstTypeName))
        firstTypeText.text = firstTypeName
        secondTypeText.visibility = View.GONE
        secondType.visibility = View.GONE
    }

    private fun FragmentDetailBinding.displayTwoTypePokemonFrom(result: DetailState.Success<PokemonDetail>) {
        val firstTypeName = result.data.types?.get(0)?.typeName
        val secondTypeName = result.data.types?.get(1)?.typeName
        firstType.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                ColorTypePicker.getTypeColor(firstTypeName)
            )
        )
        firstIconType.setImageResource(IconTypePicker.getIcon(firstTypeName))
        secondType.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                ColorTypePicker.getTypeColor(secondTypeName)
            )
        )
        secondIconType.setImageResource(
            IconTypePicker.getIcon(
                secondTypeName
            )
        )

        firstTypeText.text = firstTypeName
        secondTypeText.text = secondTypeName
    }

    private fun animateStatsBars(stats: List<Stat>?) = stats?.forEach { stat ->
        when (stat.statName) {
            HP -> binding.hpProgress reach stat.statValue
            ATTACK -> binding.attackProgress reach stat.statValue
            DEFENSE -> binding.defenseProgress reach stat.statValue
            SPEED -> binding.speedProgress reach stat.statValue
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        binding.errorState.retryButton.setOnClickListener { viewModel.retry() }
    }

    private fun handleArguments() {
        val isPokemonIdAvailable = arguments?.containsKey(POKEMON_NAME_KEY)
        if (isPokemonIdAvailable == true) {
            val pokemonName = arguments?.getString(POKEMON_NAME_KEY) ?: ""
            viewModel.getPokemonName(pokemonName)
            setTransitionName(binding.cardViewEnd, pokemonName)
        }
    }
}