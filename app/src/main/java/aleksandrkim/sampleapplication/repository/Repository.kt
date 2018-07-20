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
    val TAG = "Repository"

    private val topHeadlinesApi: TopHeadlinesApi by lazy {
        ServiceProvider.getService(TopHeadlinesApi::class.java)
    }

    fun getTopHeadlinesAll(): LiveData<List<Article>> {
        Log.d(TAG, "getTopHeadlinesAll: ")
        fetchNewArticles()
        return appDatabase.articleDao().getAllFeed()
    }

    private fun fetchNewArticles() {
        Log.d(TAG, "fetchNewArticles: ")
        topHeadlinesApi.getAllEng()
            .map { t -> t.articles }
            .subscribeOn(Schedulers.io())
            .subscribe { list -> saveNewArticles(list) }
    }

    private fun saveNewArticles(list: List<Article>) {
        Log.d(TAG, "saveNewArticles: " + list.size)
        appDatabase.articleDao().cleanUpdate(list)
    }

    companion object : SingletonHolder<AppDatabase, Repository>(::Repository)

}