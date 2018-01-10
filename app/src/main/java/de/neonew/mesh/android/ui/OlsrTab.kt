package de.neonew.mesh.android.ui

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import de.neonew.mesh.android.Olsrd
import de.neonew.mesh.android.Ping
import de.neonew.mesh.android.R
import de.neonew.mesh.android.Runner
import kotlinx.android.synthetic.main.olsr_tab.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread


class OlsrTab : Fragment() {

    private var handler = Handler()
    private lateinit var neighborsAdapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.olsr_tab, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        olsrd_run.setOnClickListener {
            Olsrd().run(context)
            update()
        }

        olsrd_kill.setOnClickListener {
            Olsrd().kill()
            update()
        }

        olsrd_force_kill.setOnClickListener {
            Olsrd().forceKill()
            update()
        }

        neighborsAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, emptyList<String>().toMutableList())
        olsrd_neighbors.adapter = neighborsAdapter

        olsrd_neighbors.setOnItemClickListener { parent, view, position, id ->
            val item = olsrd_neighbors.getItemAtPosition(position).toString()
            doAsync {
                Runner.runCommand(arrayOf("ping", "-c", "1", item),
                        { ping -> uiThread { toast("Ping to ${item} took ${Ping(ping).getMs()} ms") } },
                        { exitCode, output -> uiThread { toast("Ping to ${item} failed with ${exitCode}") } })
            }
        }

        handler.post(updateRunnable)
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

        doAsync {
            Olsrd.getNeighbors { neighbors ->
                uiThread {
                    neighborsAdapter.clear()
                    neighborsAdapter.addAll(neighbors)
                    neighborsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            update()
            handler.postDelayed(this, 3000)
        }
    }
}
