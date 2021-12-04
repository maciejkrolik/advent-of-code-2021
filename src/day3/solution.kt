package day3

import java.io.File

private val input = File("src/day3/input.txt").readLines()

fun main() {
    part1()
    part2()
}

fun part1() {
    val gammaRate = mutableListOf<Char>()
    input.transpose().forEach { line ->
        val mostCommonCharacter = line.getMostCommonCharacter() ?: throw Exception("Most common value does not exist!")
        gammaRate.add(mostCommonCharacter)
    }
    val gammaRateBinaryString = gammaRate.joinToString(separator = "")
    val gammaRateDecimal = gammaRateBinaryString.toInt(2)
    val epsilonRateDecimal = gammaRateBinaryString.map { it.digitToInt().xor(1) }.joinToString(separator = "").toInt(2)
    println(gammaRateDecimal * epsilonRateDecimal)
}

fun part2() {
    val oxygenGeneratorRating = calculateLifeSupportRatingElement(
        mostCommonValue = true,
        defaultValue = '1'
    )
    val co2ScrubberRating = calculateLifeSupportRatingElement(
        mostCommonValue = false,
        defaultValue = '0'
    )
    println(oxygenGeneratorRating * co2ScrubberRating)
}

fun calculateLifeSupportRatingElement(
    mostCommonValue: Boolean = true,
    defaultValue: Char
): Int {
    var currentNumbers: List<String> = input.toList()
    var columnIndex = 0
    while (currentNumbers.size != 1) {
        val currentNumbersTransposed = currentNumbers.transpose()
        val requiredCharacter = if (mostCommonValue) {
            currentNumbersTransposed[columnIndex].getMostCommonCharacter() ?: defaultValue
        } else {
            currentNumbersTransposed[columnIndex].getMostCommonCharacter()
                ?.digitToInt()
                ?.xor(1)
                ?.toString()
                ?.getOrNull(0) ?: defaultValue
        }
        currentNumbers = currentNumbers.filter { it[columnIndex] == requiredCharacter }
        columnIndex++
    }
    return currentNumbers.first().toInt(2)
}

fun List<String>.transpose(): List<String> {
    val lineLength = this.first().length
    val columns = mutableListOf<String>()

    for (i in 0 until lineLength) {
        val column = mutableListOf<Char>()
        this.forEach { line ->
            column.add(line[i])
        }
        columns.add(column.joinToString(separator = ""))
    }

    return columns
}

fun String.getMostCommonCharacter(): Char? {
    val countedElements = this.groupingBy { it }.eachCount()
    val maxValue = countedElements.values.maxOrNull()
    if (countedElements.values.count { it == maxValue } > 1) return null // If most common value does not exist
    return countedElements.maxByOrNull { it.value }?.key ?: throw Exception("No entries found!")
}