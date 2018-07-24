package aleksandrkim.sampleapplication.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.widget.Toast

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 1:35 PM for SampleApplication
 */

//region Toast
fun Toast.setTextAndShow(text: String) {
    setText(text)
    show()
}

fun Toast.setTextAndShow(textId: Int) {
    setText(textId)
    show()
}
//endregion

//region LiveData
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}
//endregion

fun Boolean.toSqlInt() = if (this) 1 else 0

typealias OnArticleClicked = (id: Int) -> Unit