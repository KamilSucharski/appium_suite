package common.factory

object StringFactory {

    private val ALLOWED_CHARACTERS = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    fun createString(length: Int): String {
        val builder = StringBuilder()
        for (i in 0 until length) {
            builder.append(ALLOWED_CHARACTERS[i % ALLOWED_CHARACTERS.size])
        }
        return builder.toString()
    }
}