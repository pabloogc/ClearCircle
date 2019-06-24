package com.minikorp.clearscore.core.data

import com.minikorp.clearscore.testdata.TestData
import com.minikorp.clearscore.core.data.network.NetworkScore
import com.minikorp.clearscore.core.data.network.ClearScoreApi
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

class ScoreRepositoryTest {

    @Test
    fun `items are mapped`() = runBlocking {
        val mockApi = mock<ClearScoreApi> {
            onBlocking { getScore() } doReturn NetworkScore(
                creditReportInfo = NetworkScore.CreditReportInfo(
                    score = TestData.sampleScore.score,
                    maxScoreValue = TestData.sampleScore.maxScore
                )
            )

        }
        val repo = NetworkScoreRepository(mockApi)
        val score = repo.getScore().getOrThrow()
        expectThat(score) {
            isEqualTo(TestData.sampleScore)
        }
        Unit //Must be void
    }

    @Test
    fun `failures are wrapped`() = runBlocking {
        val mockApi = mock<ClearScoreApi> {
            onBlocking { getScore() } doThrow RuntimeException("Failed request")
        }
        val repo = NetworkScoreRepository(mockApi)
        val score = repo.getScore()
        expectThat(score.exceptionOrNull()) {
            isNotNull()
        }
        Unit //Must be void
    }

    //TODO: More tests here like data validation
}