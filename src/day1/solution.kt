package day1

import java.io.File

private val input = File("src/day1/input.txt").readLines().map { it.toInt() }

fun main() {
    part1(input)
    part2()
}

fun part1(list: List<Int>) {
    var counter = 0
    list.forEachIndexed { index, currentNumber ->
        val previousNumber = list.getOrNull(index - 1) ?: return@forEachIndexed
        if (currentNumber > previousNumber) {
            counter++
        }
    }
    println(counter)
}

fun part2() {
    val listOfSums = mutableListOf<Int>()
    input.forEachIndexed { index, firstNumber ->
        val secondNumber = input.getOrNull(index + 1) ?: return@forEachIndexed
        val thirdNumber = input.getOrNull(index + 2) ?: return@forEachIndexed
        listOfSums.add(firstNumber + secondNumber + thirdNumber)
    }
    part1(listOfSums)
}