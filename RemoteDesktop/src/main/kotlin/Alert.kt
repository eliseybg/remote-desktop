import javax.swing.JOptionPane

fun exception(message: String) {
    Thread { JOptionPane.showMessageDialog(null, message) }.start()
}