package de.neonew.mesh.android

import de.neonew.mesh.android.Runner.Companion.runAsRoot

class Olsrd {

    companion object {
        fun isRunning(): Boolean {
            return runAsRoot("pidof olsrd").isNotEmpty()
        }
    }

    // Resource(R.raw.olsrd).copy(applicationContext, "olsrd")
}
