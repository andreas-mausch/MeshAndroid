package de.neonew.mesh.android

class WifiAdhoc {
    companion object {
        fun startMesh(ip: String, name: String) {
            Runner.runAsRoot("ifconfig wlan0 down")
            Runner.runAsRoot("echo \"/system/etc/wifi/bcmdhd_ibss.bin\" >> /sys/module/dhd/parameters/firmware_path")
            Runner.runAsRoot("echo \"/system/etc/wifi/nvram_net.txt\" >> /sys/module/dhd/parameters/nvram_path")
            Runner.runAsRoot("ifconfig wlan0 ${ip} netmask 255.255.0.0 up")
            Runner.runAsRoot("iw wlan0 set type ibss")
            Runner.runAsRoot("iw wlan0 ibss join ${name} 2412 00:11:22:33:44:55")
        }
    }
}
