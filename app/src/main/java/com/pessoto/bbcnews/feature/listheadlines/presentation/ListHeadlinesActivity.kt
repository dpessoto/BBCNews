package com.pessoto.bbcnews.feature.listheadlines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel.ListHeadlinesStateView
import com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel.ListHeadlinesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListHeadlinesActivity : AppCompatActivity() {

    private val viewModel: ListHeadlinesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_headlines)

        viewModel.stateView.observe(this) { stateView ->
            when (stateView) {
                is ListHeadlinesStateView.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
                is ListHeadlinesStateView.DataLoaded -> {
                    val data = stateView.data
                    Toast.makeText(this, data.totalResults.toString(), Toast.LENGTH_SHORT).show()
                }
                is ListHeadlinesStateView.Error -> {
                    val e = stateView.e
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        viewModel.getHeadline()
    }
}
