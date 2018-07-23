package aleksandrkim.sampleapplication.details

import aleksandrkim.sampleapplication.db.entities.Article
import aleksandrkim.sampleapplication.repository.Repository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import io.reactivex.Completable

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 7:48 PM for SampleApplication
 */
class DetailsFragmentVM(private val repository: Repository) : ViewModel() {

    private val articleId: MutableLiveData<Int> = MutableLiveData()

    val currentArticle: LiveData<Article?> = Transformations.switchMap(articleId, { repository.getArticleById(it ?: -1) })

    fun setArticleId(id: Int) {
        articleId.value = id
    }

    fun toggleStar(): Completable {
        return repository.starArticleById(articleId.value!!, !(currentArticle.value?.starred ?: true))
    }

    companion object {
        const val TAG = "DetailsFragmentVM"
    }


}