package de.neonew.meshandroid

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.IOUtils
import java.io.IOException


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ip.setText("10.0.0.1")

        start_mesh.setOnClickListener {
            try {
                startMesh(ip.text.toString())
            } catch (e: IOException) {
                makeText(this, e.message, LENGTH_LONG).show()
            }

            Log.i("MainActivity", "OK")
        }
    }

    private fun startMesh(ip: String) {
        runAsRoot("ifconfig wlan0 down")
        runAsRoot("echo \"/system/etc/wifi/bcmdhd_ibss.bin\" >> /sys/module/dhd/parameters/firmware_path")
        runAsRoot("echo \"/system/etc/wifi/nvram_net.txt\" >> /sys/module/dhd/parameters/nvram_path")
        runAsRoot("ifconfig wlan0 ${ip} netmask 255.255.0.0 up")
        runAsRoot("iw wlan0 set type ibss")
        runAsRoot("iw wlan0 ibss join MeshAndroid 2412")
    }

    private fun runAsRoot(command: String) {
        val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
        val returnValue = process.waitFor()
        val output = IOUtils.toString(process.inputStream)
        if (returnValue != 0) {
            throw IllegalStateException("command failed: $command; ret: $returnValue; output: $output")
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
