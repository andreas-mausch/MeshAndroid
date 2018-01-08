package de.neonew.mesh.android.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import de.neonew.mesh.android.R
import de.neonew.mesh.android.WifiAdhoc.Companion.startMesh
import kotlinx.android.synthetic.main.wifi_tab.*
import kotlinx.android.synthetic.main.wifi_tab.view.*
import java.io.IOException

class WifiTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.wifi_tab, container, false)

        view.ip.setText("10.0.0.1")
        view.name.setText("MeshAndroid")

        view.start_mesh.setOnClickListener {
            try {
                startMesh(ip.text.toString(), name.text.toString())
            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } finally {
                val wifi_status = childFragmentManager.findFragmentById(R.id.wifi_status) as WifiStatusFragment
                wifi_status.update()
            }
        }

        return view
    }
}
