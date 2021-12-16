import java.awt.event.*
import java.io.PrintWriter
import java.net.Socket
import javax.swing.JPanel

class SendEvents(
    cSocket: Socket,
    private var cPanel: JPanel,
    width: String,
    height: String
) : KeyListener, MouseMotionListener, MouseListener {
    private var w = width.toDouble()
    private var h = height.toDouble()
    private var writer = PrintWriter(cSocket.getOutputStream())

    init {
        cPanel.addKeyListener(this)
        cPanel.addMouseListener(this)
        cPanel.addMouseMotionListener(this)
    }


    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
        writer.println(Commands.PRESS_KEY.abbrev)
        writer.println(e.keyCode)
        writer.flush()
    }

    override fun keyReleased(e: KeyEvent) {
        writer.println(Commands.RELEASE_KEY.abbrev)
        writer.println(e.keyCode)
        writer.flush()
    }

    override fun mouseDragged(e: MouseEvent) {
        val xScale = w / cPanel.width
        val yScale = h / cPanel.height
        writer.println(Commands.DRAG_MOUSE.abbrev)
        writer.println(e.x * xScale)
        writer.println(e.y * yScale)
        writer.flush()
    }

    override fun mouseMoved(e: MouseEvent) {
        val xScale = w / cPanel.width
        val yScale = h / cPanel.height
        writer.println(Commands.MOVE_MOUSE.abbrev)
        writer.println(e.x * xScale)
        writer.println(e.y * yScale)
        writer.flush()
    }

    override fun mouseClicked(e: MouseEvent) {
    }

    override fun mousePressed(e: MouseEvent) {
        writer.println(Commands.PRESS_MOUSE.abbrev)
        val button = e.button
        var xButton = 16
        if (button == 3)
            xButton = 4
        writer.println(xButton)
        writer.flush()

    }

    override fun mouseReleased(e: MouseEvent) {
        writer.println(Commands.RELEASE_MOUSE.abbrev)
        val button = e.button
        var xButton = 16
        if (button == 3)
            xButton = 4
        writer.println(xButton)
        writer.flush()
    }

    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }
}