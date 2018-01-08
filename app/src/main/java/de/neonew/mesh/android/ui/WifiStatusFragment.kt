package de.neonew.mesh.android.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.neonew.mesh.android.Ifconfig
import de.neonew.mesh.android.IwInfo
import de.neonew.mesh.android.IwLink
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Runner.Companion.runAsRoot
import kotlinx.android.synthetic.main.wifi_status.*

class WifiStatusFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.wifi_status, container, false)
    }

    override fun onResume() {
        super.onResume()

        update()
    }

    fun update() {
        val ifconfig = Ifconfig(runAsRoot("ifconfig wlan0"))
        val iwLink = IwLink(runAsRoot("iw wlan0 link"))
        val iwInfo = IwInfo(runAsRoot("iw wlan0 info"))

        mode.text = iwInfo.getMode()
        ssid.text = iwLink.getSsid()
        frequency.text = iwLink.getFrequency()
        ip.text = ifconfig.getIp()
    }
}
