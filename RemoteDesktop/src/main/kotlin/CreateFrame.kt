import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.beans.PropertyVetoException
import java.net.Socket
import javax.swing.JDesktopPane
import javax.swing.JFrame
import javax.swing.JInternalFrame
import javax.swing.JPanel
import javax.swing.plaf.basic.BasicInternalFrameUI


class CreateFrame(var cSocket: Socket, var width: String, var height: String) : Thread() {
    private var frame = JFrame()
    private var desktop = JDesktopPane()
    private var internalFrame = JInternalFrame("server screen", true, true, true)
    private var cPanel = JPanel()

    init {
        start()
    }

    private fun drawGUI() {
        frame.add(desktop, BorderLayout.CENTER)
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                Server.remove(cSocket.inetAddress.hostAddress)
            }
        })
        frame.extendedState = frame.extendedState or JFrame.MAXIMIZED_BOTH
        (internalFrame.ui as BasicInternalFrameUI).northPane = null
        internalFrame.border = null
        frame.isVisible = true
        internalFrame.layout = BorderLayout()
        internalFrame.contentPane.add(cPanel, BorderLayout.CENTER)
        internalFrame.setSize(100, 100)
        desktop.add(internalFrame)
        try {
            internalFrame.isMaximum = true
        } catch (e: PropertyVetoException) {
            e.printStackTrace()
        }
        cPanel.isFocusable = true
        internalFrame.isVisible = true
    }

    override fun run() {
        val input = cSocket.getInputStream()
        drawGUI()
        ReceivingScreen(input, cPanel, cSocket.inetAddress.hostAddress)
        SendEvents(cSocket, cPanel, width, height)
    }
}