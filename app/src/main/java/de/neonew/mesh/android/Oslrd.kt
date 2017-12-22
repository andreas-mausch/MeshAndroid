package de.neonew.mesh.android

import android.content.Context
import de.neonew.mesh.android.Runner.Companion.runAsRoot
import de.neonew.mesh.android.Runner.Companion.runAsRootInBackground

class Olsrd {

    companion object {
        fun isRunning(): Boolean {
            return runAsRoot("pidof olsrd").isNotEmpty()
        }
    }

    fun run(context: Context) {
        Resource(R.raw.olsrd).copy(context, "olsrd")

        val filename = Resource.getFilename(context, "olsrd")
        runAsRootInBackground(filename + " -i wlan0")
    }

    fun kill(context: Context) {
        runAsRoot("killall olsrd")
    }
}
