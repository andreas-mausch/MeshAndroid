package de.neonew.mesh.android.ui.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Runner
import kotlinx.android.synthetic.main.onboarding_root_access.*
import java.io.IOException

class RootAccessFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.onboarding_root_access, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check_root.setOnClickListener {
            try {
                Runner.runAsRoot("ls")
                root_result.text = getString(R.string.onboarding_root_success)
                root_result.setTextColor(ContextCompat.getColor(activity!!, R.color.success))

                onboardingActivity().enableNextPage(true)
            } catch (e: Exception) {
                when (e) {
                    is IllegalStateException,
                    is IOException -> {
                        root_result.text = getString(R.string.onboarding_root_error, e.message)
                        root_result.setTextColor(ContextCompat.getColor(activity!!, R.color.failure))

                        onboardingActivity().enableNextPage(false)
                    }
                    else -> throw e
                }
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            onboardingActivity().enableNextPage(false)
        }
    }

    private fun onboardingActivity() = (activity as OnboardingActivity)
}
