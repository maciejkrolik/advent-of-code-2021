package day4

import java.io.File

data class Value(val value: Int, var isMarked: Boolean = false)
sealed class ValidationResult(
    val boards: Set<Int>? = null
) {
    class Winner(board: Set<Int>?) : ValidationResult(board)
    class NoWinner : ValidationResult()
}

private val input = File("src/day4/input.txt")
private val numbers: List<Int> = input.readLines().first().split(",").map { it.toInt() }
private val boards = input.readText().split("\r\n\r\n").drop(1).map { board ->
    board.split("\r\n").map { rows ->
        rows.split(" ")
    }.map { numbers ->
        numbers.filter { number -> number.isNotBlank() }.map { number -> Value(number.toInt()) }.toTypedArray()
    }.toTypedArray()
}

fun main() {
    part1()
    part2()
}

fun part1() {
    numbers.forEach { number ->
        boards.findAndMark(number)
        val validationResult = boards.validate()
        if (validationResult is ValidationResult.Winner) {
            val sumOfUnmarked = boards[validationResult.boards!!.first()].flatten().filter {
                !it.isMarked
            }.sumOf { it.value }
            println(number * sumOfUnmarked)
            return
        }
    }
}

fun part2() {
    val winnersIndexes = mutableSetOf<Int>()
    numbers.forEach { number ->
        boards.findAndMark(number)
        val validationResult = boards.validate()
        if (validationResult is ValidationResult.Winner) {
            winnersIndexes.addAll(validationResult.boards!!)
            if (winnersIndexes.size == boards.size) {
                val sumOfUnmarked = boards[validationResult.boards.last()].flatten().filter {
                    !it.isMarked
                }.sumOf { it.value }
                println(number * sumOfUnmarked)
                return
            }
        }
    }
}

fun List<Array<Array<Value>>>.findAndMark(number: Int) {
    this.forEachIndexed { boardIndex, board ->
        board.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, value ->
                if (value.value == number) {
                    this[boardIndex][rowIndex][columnIndex].isMarked = true
                }
            }
        }
    }
}

fun List<Array<Array<Value>>>.validate(): ValidationResult {
    val winners = mutableSetOf<Int>()
    // check rows
    this.forEachIndexed { boardIndex, board ->
        board.forEach { row ->
            if (row.all { it.isMarked }) winners.add(boardIndex)
        }
    }
    // check columns
    this.forEachIndexed { boardIndex, board ->
        for (x in 0 until 5) {
            val column = mutableListOf<Value>()
            for (y in 0 until 5) {
                column.add(board[y][x])
            }
            if (column.all { it.isMarked }) winners.add(boardIndex)
        }
    }

    return if (winners.size >= 1) {
        ValidationResult.Winner(winners)
    } else {
        ValidationResult.NoWinner()
    }
}
