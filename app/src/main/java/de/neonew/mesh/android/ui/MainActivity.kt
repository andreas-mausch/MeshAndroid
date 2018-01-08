package de.neonew.mesh.android.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
import de.neonew.mesh.android.R
import de.neonew.mesh.android.WifiAdhoc
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(SOFT_INPUT_STATE_HIDDEN)

        when (runCompatiblityCheck()) {
            true -> setCompatiblity(R.string.compatibility_check_success, R.drawable.ic_check_circle, R.color.success)
            false -> setCompatiblity(R.string.compatibility_check_failure, R.drawable.ic_warn, R.color.failure)
        }

        pager.adapter = TabPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(pager)
    }

    private fun runCompatiblityCheck(): Boolean {
        return WifiAdhoc.driverExists()
    }

    private fun setCompatiblity(text: Int, icon: Int, color: Int) {
        compatibility_check.text = getString(text)

        val drawable = ContextCompat.getDrawable(this, icon)
        drawable.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.MULTIPLY)
        compatibility_check.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    inner class TabPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> WifiTab()
                1 -> OlsrTab()
                else -> null
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Wifi"
                1 -> "Olsr"
                else -> null
            }
        }
    }
}
