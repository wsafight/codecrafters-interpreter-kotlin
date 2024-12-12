import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.size < 2) {
        System.err.println("Usage: ./your_program.sh tokenize <filename>")
        exitProcess(1)
    }

    val command = args[0]
    val filename = args[1]

    if (command != "tokenize") {
        System.err.println("Unknown command: ${command}")
        exitProcess(1)
    }

    val fileContents = File(filename).readText()

    if (fileContents.isEmpty()) {
        println("EOF  null")
        return
    }

    val tokens = mutableListOf<String>()

    for (char in fileContents) {

        val tokenType = when (char) {
            '(' -> "LEFT_PAREN"
            ')' -> "RIGHT_PAREN"
            '{' -> "LEFT_BRACE"
            '}' -> "RIGHT_BRACE"
            else -> throw RuntimeException("Unknown token: $char")
        }

        tokens.add("$tokenType $char null")
    }

    println(tokens.joinToString(separator = "\n"))
    println("EOF  null")
}
