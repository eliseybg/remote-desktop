import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket

object Server : Thread() {
    var port: Int = 0
    var connections = HashMap<String, Connection>()
    var server: ServerSocket? = null

    override fun run() {
        server = ServerSocket(port)
        while (true) {
            val socket = server?.accept()
            add(socket!!)
        }
    }

    private fun add(socket: Socket) {
        connections[socket.inetAddress.hostAddress] = Connection(socket)
        connections[socket.inetAddress.hostAddress]?.start()
    }

    fun remove(ip: String) {
        connections[ip]?.close()
        connections.remove(ip)
    }

    fun mutableConnections(): MutableList<String> {
        return try {
            connections.keys.toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}