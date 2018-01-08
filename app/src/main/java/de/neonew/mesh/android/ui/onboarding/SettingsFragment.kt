package de.neonew.mesh.android.ui.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Settings
import kotlinx.android.synthetic.main.onboarding_settings.*
import java.util.*


class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.onboarding_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Settings.setSsid(context, "MeshAndroid")
        Settings.setFrequency(context, "2412")
        Settings.setBssid(context, "02:11:22:33:44:55")
        Settings.setIP(context, generateIP())
        Settings.setSubnetmask(context, "255.0.0.0")

        onboarding_settings_ssid.text = Settings.getSsid(context)
        onboarding_settings_frequency.text = Settings.getFrequency(context)
        onboarding_settings_bssid.text = Settings.getBssid(context)
        onboarding_settings_ip.text = Settings.getIP(context)
        onboarding_settings_subnetmask.text = Settings.getSubnetmask(context)
    }

    private fun generateIP(): String {
        val random = Random()
        val part1 = random.nextInt(256)
        val part2 = random.nextInt(256)
        val part3 = random.nextInt(256)
        return "10.${part1}.${part2}.${part3}"
    }
}
