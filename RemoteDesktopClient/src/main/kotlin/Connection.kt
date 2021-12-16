import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

class Connection(host: String, port: Int, timeOut: Int = 0) : Closeable {
    private var socket = Socket()
    private var sender: DataOutputStream
    private var width = ""
    private var height = ""

    init {
        socket.connect(InetSocketAddress(host, port), timeOut)
        val input = DataInputStream(socket.getInputStream())
        val password = input.readUTF()
        sender = DataOutputStream(socket.getOutputStream())
        val passwordState = PasswordGenerator.password == password
        sender.writeBoolean(passwordState)
        if (passwordState)
            share()
        else {
            FindServer.removeConnection(socket.inetAddress.hostAddress)
            input.close()
            close()
        }
    }

    private fun share() {
        val graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val graphicsDevice = graphicsEnvironment.defaultScreenDevice
        val dimension = Toolkit.getDefaultToolkit().screenSize
        width = dimension.width.toString()
        height = dimension.height.toString()
        val rectangle = Rectangle(dimension)
        val robot = Robot(graphicsDevice)
        sender = DataOutputStream(socket.getOutputStream())
        sender.writeUTF(width)
        sender.writeUTF(height)
        SendScreen(socket, robot, rectangle)
        ReceiveEvents(socket, robot)
    }

    override fun close() {
        socket.close()
        sender.close()
    }
}