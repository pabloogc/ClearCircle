package com.minikorp.clearscore.app.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minikorp.clearscore.core.data.ScoreRepository
import com.minikorp.clearscore.core.model.Resource
import com.minikorp.clearscore.core.model.Score
import kotlinx.coroutines.launch

/**
 * View model for [HomeFragment] that holds score.
 */
class ScoreViewModel(private val scoreRepository: ScoreRepository) : ViewModel() {
    /**
     * The user score
     */
    val score = MutableLiveData<Resource<Score>>()

    /**
     * Fetch score
     *
     * Add as many layers as needed here, Interactor -> Controller -> Repository.
     *
     * Not included since I am not testing those layers in isolation, I prefer to keep this simple.
     * Real application would most likely require *at least* another abstraction
     * layer before network / database call.
     */
    fun fetchScore() {
        if (score is Resource.Loading<*>) {
            return //Already loading, nothing to do
        }

        score.postValue(Resource.Loading())
        viewModelScope.launch {
            val loadedProducts = scoreRepository.getScore()
            score.postValue(
                when {
                    loadedProducts.isSuccess -> Resource.Success(loadedProducts.getOrThrow())
                    else -> Resource.Error(loadedProducts.exceptionOrNull()!!)
                }
            )
        }
    }
}