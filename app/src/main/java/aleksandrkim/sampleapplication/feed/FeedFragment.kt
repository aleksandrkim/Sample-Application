package aleksandrkim.sampleapplication.feed

import aleksandrkim.sampleapplication.R
import aleksandrkim.sampleapplication.dummy.DummyContent
import aleksandrkim.sampleapplication.dummy.DummyContent.DummyItem
import aleksandrkim.sampleapplication.repository.Repository
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [FeedFragment.OnListFragmentInteractionListener] interface.
 */
class FeedFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private val feedAdapter: MyFeedRecyclerViewAdapter by lazy {
        MyFeedRecyclerViewAdapter(DummyContent.ITEMS, listener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

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
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        var articles = Repository.getTopHeadlinesAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    t -> Log.d(TAG, "news: " + t.size)
            }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
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
