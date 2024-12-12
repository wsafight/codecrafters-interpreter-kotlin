import java.io.File
import kotlin.system.exitProcess


fun loxError(line: Int, errorCh: String) {
    System.err.println("[line $line] Error: Unexpected character: $errorCh")
}

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

    var line = 1
    var hasError = false

    for (ch in fileContents) {
        val tokenType: String = when (ch) {
            '(' -> "LEFT_PAREN"
            ')' -> "RIGHT_PAREN"
            '{' -> "LEFT_BRACE"
            '}' -> "RIGHT_BRACE"
            ',' -> "COMMA"
            '.' -> "DOT"
            '-' -> "MINUS"
            '+' -> "PLUS"
            ';' -> "SEMICOLON"
            '*' -> "STAR"
            '\n' -> {
                line++
                ""
            }
            else -> {
                hasError = true
                loxError(line, ch.toString())
                ""
            }
        }

        if (tokenType.isEmpty()) {
            continue
        }
        tokens.add("$tokenType $ch null")
    }

    if (tokens.size > 0) {
        println(tokens.joinToString(separator = "\n"))
    }
  
    println("EOF  null")

    exitProcess(
        when (hasError) {
            true -> 65
            else -> 0
        }
    )
}
