package de.neonew.mesh.android.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Settings
import de.neonew.mesh.android.ui.onboarding.OnboardingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Settings.isOnboardingCompleted(this)) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

        window.setSoftInputMode(SOFT_INPUT_STATE_HIDDEN)

        val pages = listOf(
                FragmentPage("Wifi") { WifiTab() },
                FragmentPage("Olsr") { OlsrTab() })
        pager.adapter = FragmentPagerAdapter(supportFragmentManager, pages)
        tab_layout.setupWithViewPager(pager)
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
}
