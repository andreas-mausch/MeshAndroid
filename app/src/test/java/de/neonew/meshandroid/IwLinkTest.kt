package de.neonew.meshandroid

import junit.framework.Assert.assertEquals
import org.junit.Test

class IwLinkTest {

    val outputConnectedIbss = "Joined IBSS d6:be:52:0f:90:78 (on wlan0)\n" +
            "\tSSID: MeshAndroid\n" +
            "\tfreq: 2412"

    val outputConnectedNormal = "Connected to e8:df:70:43:fa:19 (on wlan0)\n" +
            "\tSSID: MyWirelessNetwork\n" +
            "\tfreq: 2437\n" +
            "\tsignal: -63 dBm\n" +
            "\ttx bitrate: 72.0 MBit/s"

    val outputNotConnected = "Not connected."

    @Test
    fun iw() {
        val iwLink = IwLink(outputConnectedIbss)
        assertEquals("MeshAndroid", iwLink.getSsid())
        assertEquals("2412", iwLink.getFrequency())
    }

    @Test
    fun iwOnNormalWifi() {
        val iwLink = IwLink(outputConnectedNormal)
        assertEquals("MyWirelessNetwork", iwLink.getSsid())
        assertEquals("2437", iwLink.getFrequency())
    }

    @Test
    fun iwNotConnected() {
        val iwLink = IwLink(outputNotConnected)
        assertEquals(null, iwLink.getSsid())
        assertEquals(null, iwLink.getFrequency())
    }

}

class IwInfoTest {

    val outputIbss = "Interface wlan0\n" +
            "\tifindex 4\n" +
            "\ttype IBSS\n" +
            "\twiphy 0"

    val outputManaged = "Interface wlan0\n" +
            "\tifindex 4\n" +
            "\ttype managed\n" +
            "\twiphy 0"

    @Test
    fun ibss() {
        assertEquals("IBSS", IwInfo(outputIbss).getMode())
    }

    @Test
    fun managed() {
        assertEquals("managed", IwInfo(outputManaged).getMode())
    }
}

class IfconfigTest {

    val output = "wlan0     Link encap:Ethernet  HWaddr cc:3a:61:57:93:59\n" +
            "          inet addr:10.0.0.1  Bcast:10.0.255.255  Mask:255.255.0.0 \n" +
            "          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1\n" +
            "          RX packets:347 errors:0 dropped:70 overruns:0 frame:0 \n" +
            "          TX packets:201 errors:0 dropped:0 overruns:0 carrier:0 \n" +
            "          collisions:0 txqueuelen:1000 \n" +
            "          RX bytes:225246 TX bytes:20795 \n"

    @Test
    fun ifconfig() {
        val ifconfig = Ifconfig(output)
        assertEquals("10.0.0.1", ifconfig.getIp())
    }
}
