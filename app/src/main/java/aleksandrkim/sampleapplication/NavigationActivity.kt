package aleksandrkim.sampleapplication

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 3:08 PM for SampleApplication
 */
interface NavigationActivity {

    fun showFragmentByTag(tag: String)

    fun hideFragmentByTag(tag: String)
    fun hideCurrentFragment()

}