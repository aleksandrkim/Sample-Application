package aleksandrkim.sampleapplication.db.dao

import aleksandrkim.sampleapplication.db.models.Article
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 12:14 AM for SampleApplication
 */
@Dao
abstract class ArticleDao : BaseDao<Article>() {

    @Query("SELECT * FROM " + Article.TABLE_NAME)
    abstract fun getAllFeed(): LiveData<List<Article>>

    @Query("SELECT * FROM " + Article.TABLE_NAME + " WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Article?>

    @Query("DELETE FROM " + Article.TABLE_NAME)
    abstract fun deleteAll()

    @Query("DELETE FROM " + Article.TABLE_NAME + " WHERE starred = 0")
    abstract fun deleteAllButStarred()

    @Query("UPDATE " + Article.TABLE_NAME + " SET starred = :star  WHERE id = :id")
    abstract fun starById(id: Int, star: Int)

    @Transaction
    open fun cleanUpdate(list: List<Article>) {
        deleteAll()
        add(list)
    }

    @Transaction
    open fun cleanUpdateButStarred(list: List<Article>) {
        deleteAllButStarred()
        add(list)
    }

    @Query("SELECT COUNT(*) FROM " + Article.TABLE_NAME)
    abstract override fun count(): Int

}