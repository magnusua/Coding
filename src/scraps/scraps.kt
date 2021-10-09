package scraps


import java.util.Collections
import kotlin.math.abs

class scraps {

}

fun main2() {
    var inputArray: Array<Array<String>> = arrayOf()
    val n = readLine()!!.toInt()
    for (i in 0 until n) {
        val strings = readLine()!!.split(' ').toTypedArray()
        inputArray += strings
    }
    println("${inputArray.first().first()} ${inputArray.first().last()}")
    println("${inputArray.last().first()} ${inputArray.last().last()}")
}

fun main3() = when (readLine()!!.toInt()) {
    in 1..4 -> "few"
    in 5..9 -> "several"
    in 10..19 -> "pack"
    in 20..49 -> "lots"
    in 50..99 -> "horde"
    in 100..249 -> "throng"
    in 250..499 -> "swarm"
    in 500..999 -> "zounds"
    in 1000..Int.MAX_VALUE -> "legion"
    else -> "no army"
}.let { println(it) }


//Write a program that reads an A list of integers and cyclically shifts the elements of the list to the right:
//1 number = length of array numbers
fun main4() = println(List(readLine()!!.toInt()) { readLine()!!.toInt() }
    .rotate(1).joinToString().replace(",", ""))

//import java.util.Collections
fun <T> List<T>.rotate(distance: Int) =
    toList().also {
        Collections.rotate(it, distance)
    }

//Write a program that reads a list of integers and outputs the number of triples in the list.
//1 number = length of array numbers
fun main5() = println(List(readLine()!!.toInt()) { readLine()!!.toInt() }.run {
    (0..this.size - 3).count { this[it] + 1 == this[it + 1] && this[it + 1] + 1 == this[it + 2] }
})

//Write a program that reads a number and prints YES if it is positive. Otherwise, the program should print NO.
fun main6() = readLine()!!.toInt().let { if (it > 0) "YES" else "NO" }.let(::print)

//import kotlin.math.abs
//There are coordinates of two queens on a chessboard. Find out, whether they take each other.
// Taking in chess means attacking. The queen moves any number of vacant squares horizontally,
// vertically, or diagonally.

fun main7() {
    val (x1, y1) = readLine()!!.split(" ").map { str -> str.toInt() }
    val (x2, y2) = readLine()!!.split(" ").map { str -> str.toInt() }
    println(if (x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y1 - y2)) "YES" else "NO")
}

//Leap year

fun main8() = readLine()!!.toInt()
    .let { if (it % 4 == 0 && it % 100 != 0 || it % 100 == 0 && it % 400 == 0) "Leap" else "Regular" }.let(::print)

//You have a sequence of integer numbers that ends with 0. Find the largest element of this sequence.
fun main9() = println(generateSequence { readLine()!!.toInt() }.takeWhile { it != 0 }.maxOrNull())

//Write a program that reads a sequence of numbers of undefined size and prints the largest number
// and the position of its first occurrence. The position starts from 1.
fun main10() {
    val numbers: MutableList<Int> = mutableListOf()
    var input: Int? = Int.MIN_VALUE
    while (input != null) {
        numbers.add(input)
        input = readLine()?.toIntOrNull()
    }
    val max = numbers.maxOrNull()
    println("$max ${numbers.indexOfFirst { it == max }}")
}

//Collatz conjecture
//You have a natural number n. Generate a sequence of integers, described in the Collatz conjecture:
//If n is an even number, divide it in half, if it is odd, multiply it by 3 and add 1.
// Repeat this operation until you get 1 as a result.

fun main11() = println(mutableListOf(readLine()!!.toInt()).run {
    while (last() != 1) this.add(
        if (last() % 2 == 0) {
            last() / 2
        } else last() * 3 + 1
    )
    this.joinToString(" ")
})
