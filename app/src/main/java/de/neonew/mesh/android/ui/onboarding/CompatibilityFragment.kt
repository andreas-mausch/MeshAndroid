package de.neonew.mesh.android.ui.onboarding

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.neonew.mesh.android.R
import de.neonew.mesh.android.WifiAdhoc
import kotlinx.android.synthetic.main.onboarding_compatibility.*

class CompatibilityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.onboarding_compatibility, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (runCompatiblityCheck()) {
            true -> setCompatiblity(R.string.compatibility_check_success, R.drawable.ic_check_circle, R.color.success)
            false -> setCompatiblity(R.string.compatibility_check_failure, R.drawable.ic_warn, R.color.failure)
        }
    }

    private fun runCompatiblityCheck(): Boolean {
        return WifiAdhoc.driverExists()
    }

    private fun setCompatiblity(text: Int, icon: Int, color: Int) {
        compatibility_check.text = getString(text)

        val drawable = ContextCompat.getDrawable(activity!!, icon)
        drawable?.setColorFilter(ContextCompat.getColor(activity!!, color), PorterDuff.Mode.MULTIPLY)
        compatibility_check.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}
