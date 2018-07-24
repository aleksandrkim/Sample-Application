package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.entities.Article
import aleksandrkim.sampleapplication.util.OnArticleClicked
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(private var list: List<Article>?,
                  private val clickListener: OnArticleClicked) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { article ->
            holder.bind(article)
            holder.view.setOnClickListener { clickListener(article.id) }
        }
    }

    fun setList(newList: List<Article>) {
        if (this.list == null) {
            this.list = newList
            notifyItemRangeInserted(0, newList.size)
        } else {
            Article.getDiffResult(list!!, newList).run {
                list = newList
                dispatchUpdatesTo(this@FeedAdapter)
            }
        }
        Log.d(TAG, "setList: " + newList.size)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var id: Int = 20
        val title: TextView = view.title
        val content: TextView = view.content

        fun bind(article: Article) {
            id = article.id
            title.text = article.title
            content.text = article.description
        }
    }

    companion object {
        const val TAG = "FeedAdapter"
    }
}
