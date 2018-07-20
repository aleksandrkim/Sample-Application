package aleksandrkim.sampleapplication.repository

import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.network.ServiceProvider
import aleksandrkim.sampleapplication.network.TopHeadlinesApi
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 9:48 PM for SampleApplication
 */
object Repository {
    const val TAG = "Repository"

    private val topHeadlinesApi: TopHeadlinesApi by lazy {
        ServiceProvider.getService(TopHeadlinesApi::class.java)
    }

    fun getTopHeadlinesAll(): Observable<List<Article>> {
        Log.d(TAG, "getTopHeadlinesAll: ")

        return topHeadlinesApi.getAll("en").toObservable()
            .map { t -> t.articles }
            .subscribeOn(Schedulers.io())
    }

}