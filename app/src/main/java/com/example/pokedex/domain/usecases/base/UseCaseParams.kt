package com.example.pokedex.domain.usecases.base

sealed class UseCaseParams

object NoParams : UseCaseParams()

data class Result<Type>(val data: Type, val error: String?) : UseCaseParams()
