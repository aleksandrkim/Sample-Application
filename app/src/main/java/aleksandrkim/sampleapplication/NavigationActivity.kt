package aleksandrkim.sampleapplication

import android.support.v4.app.Fragment

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 3:08 PM for SampleApplication
 */
interface NavigationActivity {

    fun goToFeed()

    fun launchFragment(fragment: Fragment, tag: String)

}