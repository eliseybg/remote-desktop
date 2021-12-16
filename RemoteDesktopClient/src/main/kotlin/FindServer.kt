import java.lang.Exception
import java.util.HashMap

object FindServer : Thread() {
    var subnet: String = ""
    var password: String = ""
    var port: Int = 0
    private val connections = HashMap<String, Connection?>()
    @Volatile
    var lastDelete = ""

    fun removeConnection(ip: String) {
        connections.remove(ip)
        lastDelete = ip
    }

    override fun run() {
        while (true) {
            for (i in 1..255) {
                val host = subnet + i
                if(host == lastDelete) {
                    removeConnection(host)
                    lastDelete = ""
                }
                if (!connections.containsKey(host)) {
                    SearchUsersInLan(host).start()
                }
            }
            sleep(1000)
            System.gc()
        }
    }

    private class SearchUsersInLan(var ip: String) : Thread() {
        override fun run() {
            try {
                connections[ip] = null
                val connection = Connection(ip, port, 1000)
                connections[ip] = connection
            }  catch (ignored: Exception) {
                connections.remove(ip)
            }
        }
    }
}