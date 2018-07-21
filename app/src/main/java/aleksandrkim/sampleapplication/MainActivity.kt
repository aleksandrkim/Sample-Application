package aleksandrkim.sampleapplication

import aleksandrkim.sampleapplication.feed.FeedFragment
import aleksandrkim.sampleapplication.util.OnListItemClicked
import aleksandrkim.sampleapplication.util.setTextAndShow
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnListItemClicked, NavigationActivity {

    private lateinit var shortToast: Toast

//    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shortToast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null)
            navigation.selectedItemId = R.id.navigation_home

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.navigation_home -> {
                shortToast.setTextAndShow(R.string.title_home)
//                hideCurrentFragment()
//                showFragmentByTag(FeedFragment.TAG)
                goToFeed()
                true
            }
            R.id.navigation_dashboard -> {
                shortToast.setTextAndShow(R.string.title_dashboard)
//                hideCurrentFragment()
                true
            }
            R.id.navigation_notifications -> {
                shortToast.setTextAndShow(R.string.title_notifications)
//                hideCurrentFragment()
                true
            }
            else -> false
        }
    }

//    private fun addInitialFragments() {
//        Log.d(TAG, "addInitialFragments: ")
//        val feedFragment = FeedFragment.newInstance()
//
//        currentFragment = supportFragmentManager.run {
//            beginTransaction()
//                .add(R.id.frame, feedFragment, FeedFragment.TAG)
//                .commitNow()
//
//            findFragmentByTag(FeedFragment.TAG)
//        }
//    }

//    override fun showFragmentByTag(tag: String) {
//        Log.d(TAG, "showFragmentByTag: $tag")
//
//        val fragment = supportFragmentManager.findFragmentByTag(tag)
//
//        supportFragmentManager.beginTransaction()
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .show(fragment)
//            .commit()
//
//        currentFragment = fragment
//    }

//    override fun hideFragmentByTag(tag: String) {
//        Log.d(TAG, "hideFragmentByTag: $tag")
//
//        val fragment = supportFragmentManager.findFragmentByTag(tag)
//
//        supportFragmentManager.beginTransaction()
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .hide(fragment)
//            .commit()
//    }
//
//    override fun hideCurrentFragment() {
//        Log.d(TAG, "hideCurrentFragment: ")
//
//        currentFragment = currentFragment?.let {
//            supportFragmentManager.beginTransaction().hide(it).commit()
//            null
//        }
//    }

    override fun goToFeed() {
        Log.d(TAG, "goToFeed fragments size: " + supportFragmentManager.fragments.size)

        val feedFragment = supportFragmentManager.findFragmentByTag(FeedFragment.TAG) ?: FeedFragment.newInstance()
        replaceFragmentNoBackStack(feedFragment, FeedFragment.TAG)
    }

//    override fun goToDetails(id: Int) {
//        Log.d(TAG, "goToDetails fragments size: " + supportFragmentManager.fragments.size)
//
////        val detailsFragment = supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) ?: DetailsFragment.newInstance(id)
////        replaceFragmentWithBackstack(detailsFragment, DetailsFragment.TAG)
//        try {
//            supportFragmentManager.beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.frame, DetailsFragment.newInstance(id),
//                    DetailsFragment.TAG)
//                .addToBackStack(DetailsFragment.TAG)
//                .commit()
//        } catch (ex: Exception) {
//            Log.d(TAG, ex.toString())
//        }
//    }

    override fun launchFragment(fragment: Fragment, tag: String) {
        Log.d(TAG, "launchFragment: $tag")

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.frame, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onClick(id: Int) {
        shortToast.setTextAndShow(id.toString())
//        goToDetails(id)
    }

    private fun replaceFragmentWithBackstack(fragment: Fragment, tag: String) {
        Log.d(TAG, "replaceFragmentWithBackstack: ")

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.frame, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    private fun replaceFragmentNoBackStack(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.frame, fragment, tag)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        Log.d(TAG, "onSaveInstanceState 1: ")

        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.d(TAG, "onSaveInstanceState 2: ")
        outState?.putString("bug", "fix")
        super.onSaveInstanceState(outState)
    }

    override fun onPostResume() {
        Log.d(TAG, "onPostResume: ")

        super.onPostResume()
    }

    override fun onResumeFragments() {
        Log.d(TAG, "onResumeFragments: ")

        super.onResumeFragments()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
