package de.neonew.mesh.android

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.olsrd.*

class OlsrdFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.olsrd, container, false)

        val olsrd_run = view.findViewById<Button>(R.id.olsrd_run)
        olsrd_run.setOnClickListener {
            Olsrd().run(context)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        update()
    }

    fun update() {
        olsrd_running.text = Olsrd.isRunning().toString()
    }
}
