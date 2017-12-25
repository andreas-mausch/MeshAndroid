package de.neonew.mesh.android

import android.content.Context
import de.neonew.mesh.android.Resource.Companion.getDirectory
import de.neonew.mesh.android.Runner.Companion.runAsRoot
import de.neonew.mesh.android.Runner.Companion.runAsRootInBackground

class Olsrd {

    companion object {
        fun isRunning(): Boolean {
            return runAsRoot("pidof olsrd").isNotEmpty()
        }
    }

    fun run(context: Context) {
        Resource(R.raw.olsrd).copy(context, "olsrd", true)
        Resource(R.raw.olsrd_jsoninfo).copy(context, "olsrd_jsoninfo.so.1.1", true)
        Resource(R.raw.olsrd_conf).copy(context, "olsrd.conf", false)

        runAsRootInBackground("./olsrd -f olsrd.conf", getDirectory(context))
    }

    fun kill() = runAsRoot("killall olsrd")
    fun forceKill() = runAsRoot("killall -9 olsrd")
}
