package de.neonew.mesh.android

import kotlin.text.RegexOption.MULTILINE

class IwLink(val output: String) {
    fun getSsid(): String? = Regex("\\tSSID: (.+)").find(output)?.groupValues?.get(1)
    fun getFrequency(): String? = Regex("\\tfreq: (\\w+)").find(output)?.groupValues?.get(1)
}

class IwInfo(val output: String) {
    fun getMode(): String? = Regex("\ttype (\\w+)").find(output)?.groupValues?.get(1)
}

class Ifconfig(val output: String) {
    fun getIp(): String? = Regex("addr:([0-9.]*)").find(output)?.groupValues?.get(1)
    fun getMask(): String? = Regex(" Mask:([0-9.]*)").find(output)?.groupValues?.get(1)
}

class Ping(val output: String) {
    fun getMs(): Int? = Regex("time=([\\d.]+) ms", MULTILINE).find(output)?.groupValues?.get(1)?.toDouble()?.toInt()
}
