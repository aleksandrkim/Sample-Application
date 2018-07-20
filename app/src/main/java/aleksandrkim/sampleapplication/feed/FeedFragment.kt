package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.db.AppDatabase
import aleksandrkim.sampleapplication.repository.Repository
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.reactivex.disposables.CompositeDisposable


class FeedFragment : Fragment() {

    private var columnCount = 1

    private val compositeDisposable = CompositeDisposable()

    private var listener: OnListItemClicked? = null
    private val feedAdapter: FeedAdapter by lazy {
        FeedAdapter(null, listener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemClicked) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d(TAG, "onCreate: ")

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        Repository.getInstance(AppDatabase.getInstance(requireContext().applicationContext))
            .getTopHeadlinesAll().observe(this, Observer { articles ->
                articles?.let {
                    feedAdapter.setList(it)
                    Log.d(TAG, "articles " + it.size)
                }
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            view.apply {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = feedAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            }
        }
        return view
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
