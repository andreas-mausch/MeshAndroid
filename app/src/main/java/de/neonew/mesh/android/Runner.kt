package de.neonew.mesh.android

import org.apache.commons.io.IOUtils
import java.io.File

class Runner {

    companion object {
        fun runCommand(command: Array<String>): String {
            var output = ""
            runCommand(command,
                    { o -> output = o },
                    { exitCode, o -> throw IllegalStateException("command failed: $command; ret: $exitCode; output: $o") })
            return output
        }

        fun runCommand(command: Array<String>, success: (String) -> Unit, failure: (Int, String) -> Unit) {
            val process = Runtime.getRuntime().exec(command)
            val exitCode = process.waitFor()
            val output = IOUtils.toString(process.inputStream)
            if (exitCode == 0) {
                success(output)
            } else {
                failure(exitCode, output)
            }
        }

        fun runAsRoot(command: String): String = runCommand(arrayOf("su", "-c", command))

        fun runInBackground(command: Array<String>, directory: String) {
            Runtime.getRuntime().exec(command, null, File(directory))
        }

        fun runAsRootInBackground(command: String, directory: String) = runInBackground(arrayOf("su", "-c", "cd " + directory + " && " + command), directory)
    }
}
