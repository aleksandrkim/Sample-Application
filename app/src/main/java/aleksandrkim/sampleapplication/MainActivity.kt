package aleksandrkim.sampleapplication

import aleksandrkim.sampleapplication.feed.FeedFragment
import aleksandrkim.sampleapplication.dummy.DummyContent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FeedFragment.OnListFragmentInteractionListener {

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addInitialFragments()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.navigation_home -> {
                Toast.makeText(this, R.string.title_home, Toast.LENGTH_SHORT).show()
                hideCurrentFragment()
                showFragmentByTag(FeedFragment.TAG)
                true
            }
            R.id.navigation_dashboard -> {
                Toast.makeText(this, R.string.title_dashboard, Toast.LENGTH_SHORT).show()
                hideCurrentFragment()
                true
            }
            R.id.navigation_notifications -> {
                Toast.makeText(this, R.string.title_notifications, Toast.LENGTH_SHORT).show()
                hideCurrentFragment()
                true
            }
            else -> false
        }
    }

    private fun addInitialFragments() {

        currentFragment = supportFragmentManager.run {
            beginTransaction()
                .add(R.id.frame, FeedFragment.newInstance(1), FeedFragment.TAG)
                .commitNow()

            findFragmentByTag(FeedFragment.TAG)
        }
        Log.d(TAG, "addInitialFragments: ")

//        hideAllFragments()
    }

    private fun showFragmentByTag(tag: String) {
        Log.d(TAG, "showFragmentByTag: $tag")

        val fragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .show(fragment)
            .commit()

        currentFragment = fragment
    }

    private fun hideCurrentFragment() {
        Log.d(TAG, "hideCurrentFragment: ")

        currentFragment = currentFragment?.let {
            supportFragmentManager.beginTransaction().hide(it).commit()
            null
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
