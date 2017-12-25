package de.neonew.mesh.android

import org.apache.commons.io.IOUtils
import java.io.File

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

        fun runInBackground(command: Array<String>, directory: String) {
            Runtime.getRuntime().exec(command, null, File(directory))
        }

        fun runAsRootInBackground(command: String, directory: String) = runInBackground(arrayOf("su", "-c", "cd " + directory + " && " + command), directory)
    }
}
