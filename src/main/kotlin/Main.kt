import java.io.File
import kotlin.system.exitProcess

fun checkArgs (args: Array<String>): Int {
    if (args.size < 2) {
        System.err.println("Usage: ./your_program.sh tokenize <filename>")
        return 1
    }

    val command = args[0]

    if (command != "tokenize") {
        System.err.println("Unknown command: $command")
        return 1
    }

    return 0
}

fun main(args: Array<String>) {
    val errCode = checkArgs(args)
    if (errCode != 0) {
        exitProcess(errCode)
    }

    val filename = args[1]
    val fileContents = File(filename).readText()

    val (hasError, tokens) = Scanner(fileContents).scanTokens()

    tokens.forEach { println(it) }

    if (hasError) {
        exitProcess(65)
    }
}
