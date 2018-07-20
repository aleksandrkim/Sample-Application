package aleksandrkim.sampleapplication.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 8:59 PM for SampleApplication
 */

@Entity(tableName = Article.TABLE_NAME, indices = [Index("id")])
data class Article(var author: String?,
                   val title: String = "",
                   val description: String? = "",
                   val url: String,
                   val ultToImage: String?,
                   val publishedAt: String,
                   @PrimaryKey(autoGenerate = true)
                   val id: Int) {

    companion object {
        const val TABLE_NAME = "articles"

        fun getDiffResult(list: List<Article>, newList: List<Article>): DiffUtil.DiffResult =
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return list.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return list[oldItemPosition].id == newList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return list[oldItemPosition] == newList[newItemPosition]
                }
            })
    }

}