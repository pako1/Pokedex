package com.example.pokedex.domain.usecases.base


interface UseCase<Type, Params> {
    /**
     * Triggers the useCase.
     *
     * [Type] Represents the return type of the result
     *
     * [Params] Represents the Params that need to be passed in order to get the data back.
     * Create your own Params class or in case no params are required pass the [NoParams].
     */
    suspend fun invoke(params: Params): Result<Type>
}




