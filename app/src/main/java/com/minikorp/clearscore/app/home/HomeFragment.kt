package com.minikorp.clearscore.app.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.minikorp.clearscore.R
import com.minikorp.clearscore.app.BaseFragment
import com.minikorp.clearscore.app.error.ErrorHandler
import com.minikorp.clearscore.core.model.Resource
import com.minikorp.clearscore.core.model.isErrorOrNull
import com.minikorp.clearscore.util.injectableViewModel
import com.minikorp.clearscore.util.toggleViewsVisibility
import kotlinx.android.synthetic.main.home_fragment.*
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment() {

    private val scoreViewModel: ScoreViewModel by injectableViewModel()
    private val errorHandler: ErrorHandler by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (scoreViewModel.score.isErrorOrNull()) {
            scoreViewModel.fetchScore()
        }

        score_error_button.setOnClickListener {
            scoreViewModel.fetchScore()
        }

        scoreViewModel.score.observe(this) {
            toggleViewsVisibility(it, score_container, score_progress_bar, score_error_button)
            when (it) {
                is Resource.Success -> {
                    score_circle.maxProgress = it.data!!.maxScore
                    score_circle.progress = it.data.score
                    score_number.text = it.data.score.toString()
                    //Don't want to deal with string placeholders here
                    score_max.text = "Out of ${it.data.maxScore}"
                }
                is Resource.Error -> score_error_button.text = errorHandler.getMessage(it.error)
            }
        }
    }
}