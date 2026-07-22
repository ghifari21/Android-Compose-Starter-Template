package com.project.common.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * A base class for repositories.
 *
 * It provides common utility functions for executing data operations,
 * such as safe API calls and flow handling.
 */
abstract class BaseRepository {

    /**
     * Executes a suspendable block of code and returns a [Flow] of [Result] with data transformation.
     *
     * @param T The type of the data returned by the block.
     * @param R The type of the data after transformation.
     * @param dispatcher The [CoroutineDispatcher] on which the block should be executed. Defaults to [Dispatchers.IO].
     * @param block The suspendable block of code to execute.
     * @param transform A lambda expression to transform the data from [T] to [R].
     * @return A [Flow] that emits [Result.success] with transformed data or [Result.failure] on error.
     */
    protected fun <T, R> safeCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T,
        transform: (T) -> R
    ): Flow<Result<R>> = flow {
        emit(Result.success(transform(block())))
    }.catch { e ->
        Timber.e(e)
        emit(Result.failure(e))
    }.flowOn(dispatcher)

    /**
     * Executes a suspendable block of code and returns a [Flow] of [Result].
     *
     * @param T The type of the data returned by the block.
     * @param dispatcher The [CoroutineDispatcher] on which the block should be executed. Defaults to [Dispatchers.IO].
     * @param block The suspendable block of code to execute.
     * @return A [Flow] that emits [Result.success] on success or [Result.failure] on error.
     */
    protected fun <T> safeCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ): Flow<Result<T>> = safeCall(dispatcher, block) { it }
}
