package de.neonew.mesh.android

import org.apache.commons.io.IOUtils

class Runner {

    companion object {
        fun runCommand(command: Array<String>): String {
            val process = Runtime.getRuntime().exec(command)
            val returnValue = process.waitFor()
            val output = IOUtils.toString(process.inputStream)
            if (returnValue != 0) {
                throw IllegalStateException("command failed: $command; ret: $returnValue; output: $output")
            }
            return output
        }

        fun runAsRoot(command: String): String = runCommand(arrayOf("su", "-c", command))

        fun runInBackground(command: Array<String>) {
            Runtime.getRuntime().exec(command)
        }

        fun runAsRootInBackground(command: String) = runInBackground(arrayOf("su", "-c", command))
    }
}
