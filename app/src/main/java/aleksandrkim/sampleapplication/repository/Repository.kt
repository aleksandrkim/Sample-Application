package aleksandrkim.sampleapplication.repository

import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.network.ServiceProvider
import aleksandrkim.sampleapplication.network.TopHeadlinesApi
import aleksandrkim.sampleapplication.util.SingletonHolder
import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.schedulers.Schedulers

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 9:48 PM for SampleApplication
 */
class Repository(val appDatabase: AppDatabase) {

    private val topHeadlinesApi: TopHeadlinesApi by lazy {
        ServiceProvider.getService(TopHeadlinesApi::class.java)
    }

    fun getTopHeadlinesAll(): LiveData<List<Article>> {
        Log.d(TAG, "getTopHeadlinesAll: ")
        return appDatabase.articleDao().getAllFeed()
    }

    fun fetchNewArticles() {
        Log.d(TAG, "fetchNewArticles: ")
        topHeadlinesApi.getAllEng()
            .map { t -> t.articles }
            .subscribeOn(Schedulers.io())
            .subscribe ({ list ->
                    Log.d(Companion.TAG, "fetchNewArticles: ")
                    updateArticlesDb(list)
            })
    }

    private fun updateArticlesDb(list: List<Article>) {
        Log.d(TAG, "updateArticlesDb: " + list.size)
        appDatabase.articleDao().cleanUpdate(list)
    }

    companion object : SingletonHolder<AppDatabase, Repository>(::Repository) {
        const val TAG = "Repository"
    }

}