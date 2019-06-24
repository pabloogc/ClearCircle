package com.minikorp.clearscore.core.model

import com.minikorp.clearscore.core.data.network.NetworkScore

/**
 * The score given to the user.
 */
data class Score(
    val score: Int,
    val maxScore: Int
) {
    companion object {
        /**
         * Mapper/factory function. Since transformation is a pure function
         * this works just fine, no need for more complicated
         * abstractions like mapper classes.
         */
        fun fromNetworkScore(networkScore: NetworkScore): Score {
            return Score(
                score = networkScore.creditReportInfo.score,
                maxScore = networkScore.creditReportInfo.maxScoreValue
            )
        }
    }
}
