package com.minikorp.clearscore.app.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.minikorp.clearscore.R
import com.minikorp.clearscore.app.home.HomeFragment
import com.minikorp.clearscore.app.home.ScoreViewModel
import com.minikorp.clearscore.app.view.CircularProgressView
import com.minikorp.clearscore.core.data.ScoreRepository
import com.minikorp.clearscore.core.model.Resource
import com.minikorp.clearscore.core.model.Score
import com.minikorp.clearscore.util.KodeinViewModelFactory
import com.minikorp.clearscore.util.bindViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import strikt.api.expect
import strikt.assertions.isEqualTo

/**
 * Basic UI test to showcase how they could be implemented
 * overriding view model creation, mutating live data
 * and then testing UI.
 *
 * Strategy would be similar for other views / fragments.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private fun createHomeFragment(currentScore: Int, maxScore: Int) {
        val repository = object : ScoreRepository {
            override suspend fun getScore(): Result<Score> {
                return Result.success(Score(currentScore, maxScore))
            }
        }
        val kodein = Kodein.lazy {
            bind<KodeinViewModelFactory>() with singleton { KodeinViewModelFactory(kodein.direct) }
            bindViewModel<ScoreViewModel>() with provider {
                ScoreViewModel(repository)
//                    .apply {
//                        score.postValue(
//                            Resource.Success(
//                                Score(score = currentScore, maxScore = maxScore)
//                            )
//                        )
//                    }
            }
        }
        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            HomeFragment().apply { setTestKodein(kodein) }
        }
    }

    @Test
    fun score_has_correct_values() {
        createHomeFragment(currentScore = 30, maxScore = 100)

        onView(withId(R.id.score_circle)).check { view, _ ->
            view as CircularProgressView
            expect {
                that(view.progress) {
                    isEqualTo(30)
                }
                that(view.maxProgress) {
                    isEqualTo(100)
                }
            }
        }

        //More UI checks..
    }
}