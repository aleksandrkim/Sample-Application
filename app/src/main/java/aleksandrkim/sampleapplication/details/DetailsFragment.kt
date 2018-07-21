package aleksandrkim.sampleapplication.details


import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.repository.Repository
import aleksandrkim.sampleapplication.util.VMFactoryWithRepository
import aleksandrkim.sampleapplication.util.reObserve
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private var articleId: Int = -1

    private val detailsFragmentVM by lazy {
        ViewModelProviders.of(this, VMFactoryWithRepository(Repository.getInstance
            (AppDatabase.getInstance(requireContext().applicationContext)))).get(DetailsFragmentVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d(TAG, "onCreate: ")

        arguments?.let {
            articleId = it.getInt(KEY_ARTICLE_ID)
        }
        detailsFragmentVM.setArticleId(articleId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
        subscribeToCurrentArticle()
        super.onActivityCreated(savedInstanceState)
    }

    private fun subscribeToCurrentArticle() {
        detailsFragmentVM.currentArticle.reObserve(this, Observer {
            it?.let { bindArticleInfo(it) } ?: Log.d(TAG, "subscribeToCurrentArticle: null")

        })
    }

    private fun bindArticleInfo(article: Article) {
        Log.d(TAG, "bindArticleInfo: " + article.id)

        author.text = article.author
        title.text = article.title
        description.text = article.description
        url.text = article.url
        urlImage.text = article.urlToImage
        publishedAt.text = article.publishedAt
    }

    companion object {
        const val TAG = "DetailsFragment"
        private const val KEY_ARTICLE_ID = "article_id"

        @JvmStatic
        fun newInstance(id: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ARTICLE_ID, id)
                }
            }
    }
}
