import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import javax.swing.JOptionPane


const val port = 4001

fun main() {
    mainScreen()
}

fun mainScreen() = Window(
    title = "Remote Access",
    size = IntSize(640, 520),
    resizable = false
) {
    var messages by remember { mutableStateOf(mutableListOf<String?>(null)) }
    Server.port = port
    if (!Server.isAlive)
        Server.start()

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(vertical = 10.dp).align(Alignment.CenterHorizontally)) {
            Text("Remass", textAlign = TextAlign.Center, fontSize = 28.sp)
        }
        Row(Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally)) {
            Text("Remote Access", textAlign = TextAlign.Center, fontSize = 12.sp)
        }
        Row(Modifier.padding(vertical = 20.dp).align(Alignment.CenterHorizontally)) {
            Text(
                "List of connected users",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if (messages.size == 0 || messages[0] != null)
                messages.forEach { message ->
                    Button(modifier = Modifier.padding(1.dp).height(30.dp).fillMaxWidth(),
                        onClick = {
                            val password = getPassword() ?: return@Button
                            Server.connections[message]?.checkPassword(password)
                        }) {
                        Text(message!!)
                    }
                }
        }
    }

    Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            messages = Server.mutableConnections().toMutableList()
        }
    }, 1000, 100)
}

fun getPassword(): String? {
    return JOptionPane.showInputDialog(
        null, "Enter password", null,
        JOptionPane.PLAIN_MESSAGE, null, null, null
    ) as String?
}