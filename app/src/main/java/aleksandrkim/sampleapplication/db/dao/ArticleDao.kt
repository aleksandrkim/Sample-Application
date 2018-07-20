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

//    @Query("SELECT * FROM " + Article.TABLE_NAME + " ORDER BY createdTime DESC")
//    abstract fun getNotesPagedLastCreatedFirst(): DataSource.Factory<Int, Note>

    @Query("SELECT * FROM " + Article.TABLE_NAME)
    abstract fun getAllFeed(): LiveData<List<Article>>

    @Query("SELECT * FROM " + Article.TABLE_NAME + " WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Article>

//    @Query("SELECT * FROM " + Article.TABLE_NAME + " WHERE id = :id")
//    abstract fun getNoteById(id: Int): Article?
//
//    @Query("SELECT * FROM " + Article.TABLE_NAME + " ORDER BY createdTime DESC LIMIT 1")
//    abstract fun getLatestNote(): Article

    @Query("DELETE FROM " + Article.TABLE_NAME)
    abstract fun deleteAll()

    @Transaction
    open fun cleanUpdate(list: List<Article>){
        deleteAll()
        add(list)
    }

//    @Query("DELETE FROM " + Article.TABLE_NAME + " WHERE id = :id")
//    abstract fun delete(id: Int)

    @Query("SELECT COUNT(*) FROM " + Article.TABLE_NAME)
    abstract override fun count(): Int
}