package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.repository.Repository
import aleksandrkim.sampleapplication.util.OnListItemClicked
import aleksandrkim.sampleapplication.util.VMFactoryWithRepository
import aleksandrkim.sampleapplication.util.reObserve
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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


class FeedFragment : Fragment() {
    private var columnCount = 1

    private var listener: OnListItemClicked? = null

    private val feedFragmentVM by lazy {
        ViewModelProviders.of(this, VMFactoryWithRepository(Repository.getInstance
            (AppDatabase.getInstance(requireContext().applicationContext)))).get(FeedFragmentVM::class.java)
    }

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(null, listener) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemClicked)
            listener = context
        else
            throw RuntimeException(context.toString() + " must implement OnListItemClicked")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d(TAG, "onCreate: ")

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
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
                Log.d(TAG, "subscribeToFeed: " + it.size)
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TAG = "FeedFragment"
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            FeedFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
