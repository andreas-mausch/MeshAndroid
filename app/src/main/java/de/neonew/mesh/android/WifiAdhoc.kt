package de.neonew.mesh.android

import de.neonew.mesh.android.Runner.Companion.runAsRoot
import java.io.File

class WifiAdhoc {
    companion object {
        fun startMesh(ssid: String, frequency: String, bssid: String, ip: String, subnetmask: String) {
            runAsRoot("ifconfig wlan0 down")
            runAsRoot("echo \"/system/etc/wifi/bcmdhd_ibss.bin\" >> /sys/module/dhd/parameters/firmware_path")
            runAsRoot("echo \"/system/etc/wifi/nvram_net.txt\" >> /sys/module/dhd/parameters/nvram_path")
            runAsRoot("ifconfig wlan0 ${ip} netmask ${subnetmask} up")
            runAsRoot("iw wlan0 set type ibss")
            runAsRoot("iw wlan0 ibss join ${ssid} ${frequency} ${bssid}")
        }

        fun driverExists(): Boolean {
            return File("/system/etc/wifi/bcmdhd_ibss.bin").exists() and File("/system/etc/wifi/nvram_net.txt").exists()
        }
    }
}
