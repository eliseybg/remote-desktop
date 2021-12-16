import java.awt.Rectangle
import java.awt.Robot
import java.net.Socket
import java.net.SocketException
import javax.imageio.ImageIO

class SendScreen(
    private val socket: Socket, private val robot: Robot,
    private val rectangle: Rectangle
) : Thread() {
    private val output = socket.getOutputStream()

    init {
        start()
    }

    override fun run() {
        try {
            while (true) {
                val bufferedImage = robot.createScreenCapture(rectangle)
                ImageIO.write(bufferedImage, "jpeg", output)
                sleep(50)
            }
        } catch (e: SocketException) {
            FindServer.removeConnection(socket.inetAddress.hostAddress)
        }
    }
}
