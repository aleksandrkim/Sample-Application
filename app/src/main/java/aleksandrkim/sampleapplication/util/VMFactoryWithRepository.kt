package aleksandrkim.sampleapplication.util

import aleksandrkim.sampleapplication.feed.FeedFragmentVM
import aleksandrkim.sampleapplication.repository.Repository
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 5:40 PM for SampleApplication
 */
class VMFactoryWithRepository(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == FeedFragmentVM::class.java)
            FeedFragmentVM(repository) as T
        else
            super.create(modelClass)
    }
}