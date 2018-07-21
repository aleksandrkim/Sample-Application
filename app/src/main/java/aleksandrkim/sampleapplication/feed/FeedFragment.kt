package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.NavigationActivity
import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.details.DetailsFragment
import aleksandrkim.sampleapplication.repository.Repository
import aleksandrkim.sampleapplication.util.OnListItemClicked
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


class FeedFragment : Fragment(), OnListItemClicked {
//    private var listener: OnListItemClicked? = null

    private val navigationActivity by lazy { requireActivity() as NavigationActivity }

    private val feedFragmentVM by lazy {
        ViewModelProviders.of(this, VMFactoryWithRepository(Repository.getInstance
            (AppDatabase.getInstance(requireContext().applicationContext)))).get(FeedFragmentVM::class.java)
    }

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(null, this) }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnListItemClicked)
//            listener = context
//        else
//            throw RuntimeException(context.toString() + " must implement OnListItemClicked")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d(TAG, "onCreate: ")

        init()
    }

    private fun init() {
        feedFragmentVM.fetchNewArticles()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)
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

//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    override fun onClick(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        navigationActivity.launchFragment(detailsFragment, DetailsFragment.TAG)
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
