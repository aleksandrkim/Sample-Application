package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.db.entities.Article
import aleksandrkim.sampleapplication.network.models.ProcessedResponse
import aleksandrkim.sampleapplication.network.models.TopHeadlinesResponse
import aleksandrkim.sampleapplication.repository.Repository
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 12:50 PM for SampleApplication
 */
class FeedFragmentVM(private val repository: Repository) : ViewModel() {

    var topAllLive: LiveData<List<Article>> = repository.getTopHeadlinesAll()
        private set

    fun fetchNewArticles() {
        repository.fetchNewArticles().subscribe(
            { response ->
                when (response) {
                    is ProcessedResponse.SuccessfulResponse -> {
                        Log.d(Repository.TAG, "fetchNewArticles success: " + response.toString())
                        repository.updateArticlesDb((response.content as TopHeadlinesResponse).articles!!)
                    }
                    is ProcessedResponse.RateLimited -> Log.d(TAG, "rate limited: ")
                    is ProcessedResponse.ApiKeyInvalid -> Log.d(TAG, "api key invalid: ")
                    is ProcessedResponse.BadRequest -> Log.d(TAG, "bad request: ")
                    is ProcessedResponse.UnexpectedError -> Log.d(TAG, "Unexpected error")
                    else -> {
                        Log.d(Repository.TAG, "fetchNewArticles error: " + response.toString())
                    }
                }
            },
            { throwable ->
                Log.d(Repository.TAG, "fetchNewArticles exc:\n", throwable)
            }
        )
    }

    fun removeAllObs(owner: LifecycleOwner) {
        topAllLive.removeObservers(owner)
    }

    companion object {
        const val TAG = "FeedFragmentVM"
    }
}