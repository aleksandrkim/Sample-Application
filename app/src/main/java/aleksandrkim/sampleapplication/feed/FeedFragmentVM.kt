package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.repository.Repository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 12:50 PM for SampleApplication
 */
class FeedFragmentVM(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application.applicationContext)
    private val repository = Repository.getInstance(db)

    var topAllLive: LiveData<List<Article>> = repository.getTopHeadlinesAll()
        private set

    fun fetchNewArticles() {
        repository.fetchNewArticles()
    }

    fun removeAllObs(owner: LifecycleOwner) {
        topAllLive.removeObservers(owner)
    }

    companion object {
        const val TAG = "FeedFragmentVM"
    }
}