package aleksandrkim.sampleapplication.db

import aleksandrkim.sampleapplication.BuildConfig
import aleksandrkim.sampleapplication.db.dao.ArticleDao
import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.util.SingletonHolder
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 12:24 AM for SampleApplication
 */

@Database(entities = arrayOf(Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object : SingletonHolder<Context, AppDatabase>({
        Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, BuildConfig.APPLICATION_ID).build()
    })

}
