package org.largong

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val path = Path.of(args[0])
    var counter = 0
    for (it in Files.walk(path)) {
        if (!Files.isReadable(it)) continue
        if (!Files.isRegularFile(it)) continue
        println(it)
        counter += Files.readAllLines(it, StandardCharsets.UTF_8).size
    }
    println("lines of code: $counter")
}