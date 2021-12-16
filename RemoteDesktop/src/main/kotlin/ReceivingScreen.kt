import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import java.io.InputStream
import java.lang.Exception
import javax.swing.JPanel

class ReceivingScreen(var oin: InputStream, var cPanel: JPanel, var ip: String) : Thread() {
    var image1: Image? = null

    init {
        start()
    }

    override fun run() {
        try {
            while (true) {
                val bytes = ByteArray(1024 * 1024)
                var count = 0
                do {
                    count += oin.read(bytes, count, bytes.size - count)
                } while (!(count > 4 && bytes[count - 2] == (-1).toByte() && bytes[count - 1] == (-39).toByte()))
                image1 = ImageIO.read(ByteArrayInputStream(bytes))
                image1 = (image1 as BufferedImage?)?.getScaledInstance(cPanel.width, cPanel.height, Image.SCALE_FAST)

                val graphics = cPanel.graphics
                graphics.drawImage(image1, 0, 0, cPanel.width, cPanel.height, cPanel)

            }
        } catch (e: Exception) {
            Server.remove(ip)
            exception("client disconnected")
        }
    }
}