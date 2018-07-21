package aleksandrkim.sampleapplication.repository

import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.db.dao.ArticleDao
import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.network.ServiceProvider
import aleksandrkim.sampleapplication.network.TopHeadlinesApi
import aleksandrkim.sampleapplication.util.SingletonHolder
import aleksandrkim.sampleapplication.util.toSqlInt
import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Completable
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

    fun fetchNewArticles() {
        Log.d(TAG, "fetchNewArticles: ")
        topHeadlinesApi.getAllEng()
            .map { t -> t.articles }
            .subscribeOn(Schedulers.io())
            .subscribe({ list ->
                Log.d(TAG, "fetchNewArticles: ")
                updateArticlesDb(list)
            })
    }

    fun getArticleById(id: Int): LiveData<Article?> {
        Log.d(TAG, "getArticleById: $id")
        return articleDao.getById(id)
    }

    private fun updateArticlesDb(list: List<Article>) {
        Log.d(TAG, "updateArticlesDb: " + list.size)
        articleDao.cleanUpdateButStarred(list)
    }

    fun starArticleById(id: Int, boolean: Boolean): Completable {
        Log.d(TAG, "starArticleById: $id $boolean")
        return Completable.fromCallable { articleDao.starById(id, boolean.toSqlInt()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//        articleDao.starById(id, boolean.toSqlInt())
    }

    companion object : SingletonHolder<AppDatabase, Repository>(::Repository) {
        const val TAG = "Repository"
    }

}