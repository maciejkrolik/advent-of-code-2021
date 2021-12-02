package day2

import java.io.File

data class Command(val command: String, val value: Int)

private val input = File("src/day2/input.txt").readLines().map { line ->
    val (command, value) = line.split(" ")
    Command(command, value.toInt())
}

fun main() {
    part1()
    part2()
}

fun part1() {
    var horizontalPosition = 0
    var depth = 0

    input.forEach { command ->
        when (command.command) {
            "forward" -> horizontalPosition += command.value
            "down" -> depth += command.value
            "up" -> depth -= command.value
        }
    }

    println(horizontalPosition * depth)
}

fun part2() {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    input.forEach { command ->
        when (command.command) {
            "forward" -> {
                horizontalPosition += command.value
                depth += aim * command.value
            }
            "down" -> aim += command.value
            "up" -> aim -= command.value
        }
    }

    println(horizontalPosition * depth)
}