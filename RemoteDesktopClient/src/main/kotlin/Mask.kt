import java.net.NetworkInterface
import java.util.*

object Mask {
    var address = ""

    init {
        findAddress()
    }

    private fun findAddress() {
        val nets = NetworkInterface.getNetworkInterfaces() as Enumeration<NetworkInterface>
        while (nets.hasMoreElements()) {
            val networkInterface = nets.nextElement()
            if (networkInterface.interfaceAddresses.size == 0)
                continue
            val inetAddresses = networkInterface.inetAddresses
            while (inetAddresses.hasMoreElements()) {
                val address = inetAddresses.nextElement()
                val ip = address.hostAddress
                if (!ip.equals("127.0.0.1") && ip.matches(Regex("([0-9]{1,3}[.]){3}[0-9]{1,3}"))) {
                    this.address = ip
                    break
                }
            }
        }
    }

    fun getSubnet(): String {
        val end = address.lastIndexOf('.')
        return address.substring(0, end + 1)
    }
}