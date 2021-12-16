import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import javax.swing.JOptionPane

const val port = 4001

fun main() {
    val password = PasswordGenerator.password
    println(password)
    Thread{JOptionPane.showMessageDialog(null, password)}.start()
    FindServer.port = port
    FindServer.password = password
    FindServer.subnet = Mask.getSubnet()
    FindServer.start()
    mainScreen()
}

fun mainScreen() = Window(
    title = "",
    size = IntSize(0, 0),
    resizable = false,
    undecorated = true
)