import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception
import java.net.Socket
import java.net.SocketException

class Connection(private var socket: Socket) : Thread(), Closeable {
    var width = ""
    var height = ""
    private val input = DataInputStream(socket.getInputStream())
    private val output = DataOutputStream(socket.getOutputStream())

    override fun run() {
        try {
            val passwordState = input.readBoolean()
            if (passwordState) {
                getScreen()
            } else {
                exception("wrong password")
                throw SocketException()}
        } catch (e: Exception){
            Server.remove(socket.inetAddress.hostAddress)
            close()
        }
    }

    fun checkPassword(password: String) {
        output.writeUTF(password)
    }

    private fun getScreen() {
        width = input.readUTF()
        height = input.readUTF()
        CreateFrame(socket, width, height)
    }

    override fun close() {
        input.close()
        output.close()
        socket.close()
    }
}