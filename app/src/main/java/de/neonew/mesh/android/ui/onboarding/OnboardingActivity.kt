package de.neonew.mesh.android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Settings
import de.neonew.mesh.android.ui.FragmentPage
import de.neonew.mesh.android.ui.FragmentPagerAdapter
import de.neonew.mesh.android.ui.MainActivity
import kotlinx.android.synthetic.main.onboarding.*


class OnboardingActivity : FragmentActivity() {

    private val pages: List<FragmentPage> = listOf(
            FragmentPage("Welcome", { WelcomeFragment() }),
            FragmentPage("Access", { RootAccessFragment() }),
            FragmentPage("Compatibility", { CompatibilityFragment() }),
            FragmentPage("Settings", { SettingsFragment() }))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding)

        onboarding_pager.adapter = FragmentPagerAdapter(supportFragmentManager, pages)
        showPage(0)

        onboarding_next.setOnClickListener {
            if (onboarding_pager.currentItem == pages.size - 1) {
                Settings.onboardingCompleted(applicationContext)

                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                showPage(onboarding_pager.currentItem + 1)
            }
        }
    }

    fun enableNextPage(enable: Boolean) {
        onboarding_next.isEnabled = enable
    }

    private fun showPage(index: Int) {
        onboarding_title.text = pages[index].title

        onboarding_next.text = getString(when (index == pages.size - 1) {
            true -> R.string.done
            false -> R.string.next
        })

        onboarding_pager.currentItem = index
    }
}
