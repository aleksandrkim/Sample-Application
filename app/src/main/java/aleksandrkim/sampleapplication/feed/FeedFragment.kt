package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.NavigationActivity
import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.details.DetailsFragment
import aleksandrkim.sampleapplication.network.models.ProcessedResponse
import aleksandrkim.sampleapplication.network.models.TopHeadlinesResponse
import aleksandrkim.sampleapplication.repository.Repository
import aleksandrkim.sampleapplication.util.VMFactoryWithRepository
import aleksandrkim.sampleapplication.util.reObserve
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_feed.*
import java.net.ConnectException


class FeedFragment : Fragment() {

    private val feedFragmentVM by lazy {
        ViewModelProviders.of(this, VMFactoryWithRepository(Repository.getInstance
            (AppDatabase.getInstance(requireContext().applicationContext)))).get(FeedFragmentVM::class.java)
    }

    private lateinit var navigationActivity: NavigationActivity

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(null, { id -> onArticleClicked(id) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d(TAG, "onCreate: ")

        init()
    }

    private fun init() {
        feedFragmentVM.fetchNewArticles().subscribe(
            { response -> handleResponse(response) },
            { error ->
                if (error.cause is ConnectException) handleResponse(ProcessedResponse.ConnectionError)
                else {
                    handleResponse(ProcessedResponse.UnknownError(error.message?: "error", error.stackTrace.toString()))
                    Log.d(TAG, "fetchNewArticles error: ", error)
                }
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)
        navigationActivity = requireActivity() as NavigationActivity
        setRecycler()
        subscribeToFeed()
    }

    private fun setRecycler() {
        Log.d(TAG, "setRecycler: ")
        list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }
    }

    private fun subscribeToFeed() {
        feedFragmentVM.topAllLive.reObserve(this, Observer { articles ->
            articles?.let {
                feedAdapter.setList(it)
            }
        })
    }

    private fun onArticleClicked(id: Int) {
        navigationActivity.launchFragment(DetailsFragment.newInstance(id), DetailsFragment.TAG)
    }

    private fun handleResponse(processedResponse: ProcessedResponse) {
        navigationActivity.showShortToast(when (processedResponse) {
            is ProcessedResponse.SuccessfulResponse -> "Fetched ${(processedResponse.content as TopHeadlinesResponse).articles!!.size} articles"

            is ProcessedResponse.RateLimited -> "Rate Limited"

            is ProcessedResponse.ApiKeyInvalid -> "API key is invalid"

            is ProcessedResponse.BadRequest -> "Bad request"

            is ProcessedResponse.UnexpectedError -> "Unexpected error"

            is ProcessedResponse.ConnectionError -> "Connection error"

            is ProcessedResponse.UnknownError -> "Unknown error has occurred"
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: ")
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val TAG = "FeedFragment"

        @JvmStatic
        fun newInstance() =
            FeedFragment().apply {
                Log.d(TAG, "newInstance: ")

                arguments = Bundle().apply {}
            }
    }
}
