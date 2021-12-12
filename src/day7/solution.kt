package day7

import java.io.File
import kotlin.math.absoluteValue

val crabsPositions = File("src/day7/input.txt").readLines().first().split(",").map { it.toInt() }

fun main() {
    part1()
    part2()
}

fun part1() {
    calculateRequiredFuel(constantRate = true)
}

fun part2() {
    calculateRequiredFuel(constantRate = false)
}

fun calculateRequiredFuel(constantRate: Boolean) {
    val minCrabPosition = crabsPositions.minOf { it }
    val maxCrabPosition = crabsPositions.maxOf { it }

    var minFuel: Int? = null
    for (potentialPosition in minCrabPosition..maxCrabPosition) {
        var fuel = 0
        crabsPositions.forEach { currentPosition ->
            fuel += if (constantRate) {
                (currentPosition - potentialPosition).absoluteValue
            } else {
                calculateGrowingFuelRate((currentPosition - potentialPosition).absoluteValue)
            }
        }
        if (minFuel == null || fuel < minFuel) {
            minFuel = fuel
        }
    }
    println(minFuel)
}

fun calculateGrowingFuelRate(absoluteValue: Int): Int {
    var counter = 0
    for (i in 1..absoluteValue) counter += i
    return counter
}