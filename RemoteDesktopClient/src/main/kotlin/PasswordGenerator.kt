object PasswordGenerator {
    private var alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private var passwordSize = 10
    val password: String = generate()

    private fun generate(): String {
        var password = ""
        for (i in 0 until passwordSize) {
            val pos = (Math.random() * alphabet.length).toInt()
            password += alphabet[pos]
        }
        return password
    }
}