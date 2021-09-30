package scraps
import java.util.Collections
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