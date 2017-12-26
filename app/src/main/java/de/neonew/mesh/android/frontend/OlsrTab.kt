package de.neonew.mesh.android.frontend

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import de.neonew.mesh.android.Olsrd
import de.neonew.mesh.android.R
import kotlinx.android.synthetic.main.olsr_tab.*
import kotlinx.android.synthetic.main.olsr_tab.view.*


class OlsrTab : Fragment() {

    private var handler = Handler()
    private lateinit var neighborsAdapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.olsr_tab, container, false)

        view.olsrd_run.setOnClickListener {
            Olsrd().run(context)
            update()
        }

        view.olsrd_kill.setOnClickListener {
            Olsrd().kill()
            update()
        }

        view.olsrd_force_kill.setOnClickListener {
            Olsrd().forceKill()
            update()
        }

        neighborsAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, emptyList<String>().toMutableList())
        view.olsrd_neighbors.adapter = neighborsAdapter

        handler.post(updateRunnable)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateRunnable);
    }

    override fun onResume() {
        super.onResume()

        update()
    }

    fun update() {
        olsrd_running.text = Olsrd.isRunning().toString()
        Olsrd.getNeighbors {
            neighborsAdapter.clear()
            neighborsAdapter.addAll(it)
            neighborsAdapter.notifyDataSetChanged()
        }
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            update()
            handler.postDelayed(this, 3000)
        }
    }
}
