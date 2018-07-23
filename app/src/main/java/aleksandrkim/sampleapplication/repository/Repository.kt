package aleksandrkim.sampleapplication.repository

import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.db.dao.ArticleDao
import aleksandrkim.sampleapplication.db.entities.Article
import aleksandrkim.sampleapplication.network.ServiceProvider
import aleksandrkim.sampleapplication.network.TopHeadlinesApi
import aleksandrkim.sampleapplication.network.models.ProcessedResponse
import aleksandrkim.sampleapplication.util.ResponseValidator
import aleksandrkim.sampleapplication.util.SingletonHolder
import aleksandrkim.sampleapplication.util.toSqlInt
import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 9:48 PM for SampleApplication
 */
class Repository(private val appDatabase: AppDatabase) {

    private val articleDao: ArticleDao = appDatabase.articleDao()

    private val topHeadlinesApi: TopHeadlinesApi by lazy { ServiceProvider.getService(TopHeadlinesApi::class.java) }

    fun getTopHeadlinesAll(): LiveData<List<Article>> {
        Log.d(TAG, "getTopHeadlinesAll: ")
        return articleDao.getAllFeed()
    }

    fun fetchNewArticles(): Single<ProcessedResponse> {
        Log.d(TAG, "fetchNewArticles: ")
        return topHeadlinesApi.getAllEng()
            .map(ResponseValidator())
//            .map { t ->
//                if (t is ProcessedResponse.SuccessfulResponse) {
//                    Log.d(TAG, "fetchNewArticles: success mapped")
//                    t.content
//                }
//                else {
//                    Log.d(TAG, "fetchNewArticles: error mapped")
//                    t
//                }
//            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticleById(id: Int): LiveData<Article?> {
        Log.d(TAG, "getArticleById: $id")
        return articleDao.getById(id)
    }

    fun updateArticlesDb(list: List<Article>) {
        Log.d(TAG, "updateArticlesDb: " + list.size)
        articleDao.cleanUpdateButStarred(list)
    }

    fun starArticleById(id: Int, boolean: Boolean): Completable {
        Log.d(TAG, "starArticleById: $id $boolean")
        return Completable.fromCallable { articleDao.starById(id, boolean.toSqlInt()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object : SingletonHolder<AppDatabase, Repository>(::Repository) {
        const val TAG = "Repository"
    }

}