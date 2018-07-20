package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.models.Article
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.feed_item.view.*


class FeedAdapter(private var list: List<Article>?,
                  private val clickListener: OnListItemClicked?)
    : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    val TAG = "FeedAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { article ->
            holder.bind(article)
            holder.view.setOnClickListener { clickListener?.onClick(article.id) }
        }
    }

    fun setList(newList: List<Article>) {
        if (this.list == null) {
            this.list = newList
            notifyItemRangeInserted(0, newList.size)
        } else {
            this.list = newList
            Article.getDiffResult(list!!, newList).dispatchUpdatesTo(this)
        }
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

        override fun toString(): String {
            return super.toString() + " '" + content.text + "'"
        }
    }
}
