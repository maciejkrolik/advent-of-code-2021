package day5

import java.io.File
import kotlin.math.absoluteValue

data class Coordinates(val x: Int, val y: Int)
data class Line(val start: Coordinates, val end: Coordinates)

private val input = File("src/day5/input.txt").readLines().map {
    val (start, end) = it.split(" -> ")
    val (startX, startY) = start.split(",").map { coordinate -> coordinate.toInt() }
    val (endX, endY) = end.split(",").map { coordinate -> coordinate.toInt() }
    Line(Coordinates(startX, startY), Coordinates(endX, endY))
}

fun main() {
    part1()
    part2()
}

fun part1() {
    calculateResult(withDiagonals = false)
}

fun part2() {
    calculateResult(withDiagonals = true)
}

fun calculateResult(withDiagonals: Boolean) {
    val allCoordinates = mutableListOf<Coordinates>()
    input.forEach { line ->
        allCoordinates.addAll(line.getAllCoordinates(withDiagonals = withDiagonals))
    }
    val countedCoordinates = allCoordinates.groupingBy { it }.eachCount()
    println(countedCoordinates.filter { it.value >= 2 }.size)
}

fun Line.getAllCoordinates(withDiagonals: Boolean): List<Coordinates> {
    val coordinates = mutableListOf<Coordinates>()

    val diffX = (this.start.x - this.end.x).absoluteValue
    val diffY = (this.start.y - this.end.y).absoluteValue
    when {
        diffX == 0 -> {
            val lowerCoordinate = listOf(this.start.y, this.end.y).minOf { it }
            val higherCoordinate = listOf(this.start.y, this.end.y).maxOf { it }
            for (coordinate in lowerCoordinate..higherCoordinate) {
                coordinates.add(Coordinates(this.start.x, coordinate))
            }
        }
        diffY == 0 -> {
            val lowerCoordinate = listOf(this.start.x, this.end.x).minOf { it }
            val higherCoordinate = listOf(this.start.x, this.end.x).maxOf { it }
            for (coordinate in lowerCoordinate..higherCoordinate) {
                coordinates.add(Coordinates(coordinate, this.start.y))
            }
        }
        else -> {
            if (withDiagonals) {
                val xs = mutableListOf<Int>()
                val lowerCoordinateX = listOf(this.start.x, this.end.x).minOf { it }
                val higherCoordinateX = listOf(this.start.x, this.end.x).maxOf { it }
                for (coordinate in lowerCoordinateX..higherCoordinateX) {
                    xs.add(coordinate)
                }
                if (this.start.x > this.end.x) xs.sortDescending() else xs.sort()

                val ys = mutableListOf<Int>()
                val lowerCoordinateY = listOf(this.start.y, this.end.y).minOf { it }
                val higherCoordinateY = listOf(this.start.y, this.end.y).maxOf { it }
                for (coordinate in lowerCoordinateY..higherCoordinateY) {
                    ys.add(coordinate)
                }
                if (this.start.y > this.end.y) ys.sortDescending() else ys.sort()

                coordinates.addAll(xs.zip(ys).map { Coordinates(it.first, it.second) })
            }
        }
    }

    return coordinates
}