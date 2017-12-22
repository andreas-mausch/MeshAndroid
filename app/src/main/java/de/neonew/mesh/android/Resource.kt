package de.neonew.mesh.android

import android.content.Context
import de.neonew.mesh.android.Runner.Companion.runCommand
import java.io.File

class Resource(val resourceId: Int) {
    fun copy(context: Context, filename: String) {
        val absoluteFilename = getFilename(context, filename)
        val file = File(absoluteFilename)

        if (file.exists() && !file.delete()) {
            throw RuntimeException("couldn't delete " + absoluteFilename)
        }

        file.outputStream().use { output ->
            context.resources.openRawResource(resourceId).use { input ->
                input.copyTo(output)
            }
        }

        runCommand(arrayOf("chmod", "+x", absoluteFilename))
    }

    companion object {
        fun getFilename(context: Context, filename: String) = context.filesDir.canonicalPath + "/" + filename
    }
}
