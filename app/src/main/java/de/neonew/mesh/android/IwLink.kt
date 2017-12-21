package de.neonew.mesh.android

class IwLink(val output: String) {
    fun getSsid(): String? = Regex("\\tSSID: (.+)").find(output)?.groupValues?.get(1)
    fun getFrequency(): String? = Regex("\\tfreq: (\\w+)").find(output)?.groupValues?.get(1)
}

class IwInfo(val output: String) {
    fun getMode(): String? = Regex("\ttype (\\w+)").find(output)?.groupValues?.get(1)
}

class Ifconfig(val output: String) {
    fun getIp(): String? = Regex("addr:([0-9.]*)").find(output)?.groupValues?.get(1)
}
