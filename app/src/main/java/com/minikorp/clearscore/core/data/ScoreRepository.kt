package com.minikorp.clearscore.core.data

import com.minikorp.clearscore.core.data.network.ClearScoreApi
import com.minikorp.clearscore.core.model.Score

interface ScoreRepository {
    /**
     * Fetch score or wrap error / exception.
     */
    suspend fun getScore(): Result<Score>
}

class NetworkScoreRepository(private val api: ClearScoreApi) : ScoreRepository {

    override suspend fun getScore(): Result<Score> {
        return runCatching {
            Score.fromNetworkScore(api.getScore())
        }
    }
}