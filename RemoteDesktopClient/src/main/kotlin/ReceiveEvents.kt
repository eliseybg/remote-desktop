import java.awt.Robot
import java.net.Socket
import java.util.*
import kotlin.NoSuchElementException

class ReceiveEvents(var socket: Socket, var robot: Robot) : Thread() {
    init {
        start()
    }

    override fun run() {
        val scanner = Scanner(socket.getInputStream())
        try {
        while (true) {
            when (scanner.nextInt()) {
                -1 -> {
                    robot.mousePress(scanner.nextInt())
                }
                -2 -> {
                    robot.mouseRelease(scanner.nextInt())
                }
                -3 -> {
                    robot.keyPress(scanner.nextInt())
                }
                -4 -> {
                    robot.keyRelease(scanner.nextInt())
                }
                -5 -> {
                    robot.mouseMove(scanner.next().toDouble().toInt(),
                        scanner.next().toDouble().toInt())
                }
                -6 -> {
                    robot.mouseMove(scanner.next().toDouble().toInt(),
                        scanner.next().toDouble().toInt())
                }
            }
        }
        } catch (e: NoSuchElementException) {
            FindServer.removeConnection(socket.inetAddress.hostAddress)
        }
    }
}
