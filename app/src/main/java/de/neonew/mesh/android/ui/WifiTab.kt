package de.neonew.mesh.android.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Settings
import de.neonew.mesh.android.WifiAdhoc.Companion.startMesh
import kotlinx.android.synthetic.main.wifi_tab.*
import org.jetbrains.anko.support.v4.longToast
import java.io.IOException

class WifiTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.wifi_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_mesh.setOnClickListener {
            try {
                startMesh(Settings.getSsid(context!!), Settings.getFrequency(context!!), Settings.getBssid(context!!), Settings.getIP(context!!), Settings.getSubnetmask(context!!))
            } catch (e: IOException) {
                longToast(e.message!!)
            } finally {
                val wifi_status = childFragmentManager.findFragmentById(R.id.wifi_status) as WifiStatusFragment
                wifi_status.update()
            }
        }
    }
}
