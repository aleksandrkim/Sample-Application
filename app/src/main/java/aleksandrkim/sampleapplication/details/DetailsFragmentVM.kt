package aleksandrkim.sampleapplication.details

import aleksandrkim.sampleapplication.db.models.Article
import aleksandrkim.sampleapplication.repository.Repository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 7:48 PM for SampleApplication
 */
class DetailsFragmentVM(private val repository: Repository): ViewModel() {

    lateinit var currentArticle: LiveData<Article?>
        private set

    fun setArticleId(id: Int) {
        currentArticle = repository.getArticleById(id)
    }

    companion object {
        const val TAG = "DetailsFragmentVM"
    }


}