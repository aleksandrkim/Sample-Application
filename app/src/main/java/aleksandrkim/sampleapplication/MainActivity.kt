package aleksandrkim.sampleapplication

import aleksandrkim.sampleapplication.feed.FeedFragment
import aleksandrkim.sampleapplication.feed.OnListItemClicked
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

fun Toast.setTextAndShow(text: String) {
    setText(text); show()
}
fun Toast.setTextAndShow(textId: Int) {
    setText(textId); show()
}

class MainActivity : AppCompatActivity(), OnListItemClicked {

    private lateinit var shortToast: Toast

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shortToast = Toast.makeText(this,"", Toast.LENGTH_SHORT)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addInitialFragments()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.navigation_home -> {
                shortToast.setTextAndShow(R.string.title_home)
                hideCurrentFragment()
                showFragmentByTag(FeedFragment.TAG)
                true
            }
            R.id.navigation_dashboard -> {
                shortToast.setTextAndShow(R.string.title_dashboard)
                hideCurrentFragment()
                true
            }
            R.id.navigation_notifications -> {
                shortToast.setTextAndShow(R.string.title_notifications)
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

    override fun onClick(id: Int) {
        shortToast.setTextAndShow(id.toString())
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
