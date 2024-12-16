package com.wsafight.lox

import java.io.File
import kotlin.system.exitProcess
import com.wsafight.lox.Scanner

fun checkArgs (args: Array<String>): Int {
    if (args.size < 2) {
        System.err.println("Usage: ./your_program.sh tokenize <filename>")
        return 1
    }

    val command = args[0]

    if (command != "tokenize") {
        System.err.println("Unknown command: ${command}")
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

    val tokens = Scanner(fileContents).scanTokens()

    val errList = ArrayList<String>()
    val successList = ArrayList<Token>()

    for (token in tokens) {
        if (token.type != TokenType.ERR) {
            successList.add(token)
        } else {
            errList.add("[line ${token.line}] Error: Unexpected character: ${token.lexeme}")
        }
        if (token.type == TokenType.EOF) {
            break
        }
    }

    errList.forEach { System.err.println(it) }
    successList.forEach { println(it) }

    if (errList.isNotEmpty()) {
        exitProcess(65)
    }
}
