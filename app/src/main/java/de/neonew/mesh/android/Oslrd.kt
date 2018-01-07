package de.neonew.mesh.android

import android.content.Context
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import de.neonew.mesh.android.Resource.Companion.getDirectory
import de.neonew.mesh.android.Runner.Companion.runAsRoot
import de.neonew.mesh.android.Runner.Companion.runAsRootInBackground

class Olsrd {

    companion object {
        fun isRunning(): Boolean {
            return runAsRoot("pidof olsrd").isNotEmpty()
        }

        fun getNeighbors(callback: (List<String>) -> Unit) {
            "http://localhost:9090/all".httpGet().responseJson { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        callback(emptyList())
                    }
                    is Result.Success -> {
                        val jsonArray = result.get().obj().getJSONArray("neighbors")
                        callback((0 until jsonArray.length()).asSequence().map { jsonArray.getJSONObject(it).get("ipAddress").toString() }.toList())
                    }
                }
            }
        }
    }

    fun run(context: Context) {
        Resource(R.raw.olsrd).copy(context, "olsrd", true)
        Resource(R.raw.olsrd_jsoninfo).copy(context, "olsrd_jsoninfo.so.1.1", true)
        Resource(R.raw.olsrd_watchdog).copy(context, "olsrd_watchdog.so.0.1", true)
        Resource(R.raw.olsrd_conf).copy(context, "olsrd.conf", false)

        runAsRootInBackground("./olsrd -f olsrd.conf", getDirectory(context))
    }

    fun kill() = runAsRoot("killall olsrd")
    fun forceKill() = runAsRoot("killall -9 olsrd")
}
