//Input/Output example
//112234567890 + 112234567890 * (10000000999 - 999)
//1122345679012234567890
//a = 800000000000000000000000
//b = 100000000000000000000000
//a + b
//900000000000000000000000
// /exit
//

package calculator

import kotlin.math.pow

object Calc {

    @JvmStatic
    fun main() {
        val varMaps = mutableMapOf<String, Int>()
        val checkVar = Regex("\\s*[-]?[a-zA-Z0-9]+\\s*")
        val regexEvalNoBracket = Regex("[-]?[a-zA-Z0-9]+\\s*[-+*/^]+\\s*[-]?[a-zA-Z0-9]+")
        while (true) {
            try {
                val input = readLine()!!
                if (input.isNotEmpty()) {
                    when {
                        input.startsWith('/') -> {
                            when (input) {
                                "/exit" -> {
                                    println("Bye!")
                                    break
                                }
                                "/help" -> println("A Simple Calculator")
                                else -> println("Unknown command")
                            }
                        }
                        input.contains('=') -> assign(input, varMaps)
                        input.matches(checkVar) -> checkVar(input, varMaps)
                        regexEvalNoBracket.containsMatchIn(input) -> {
                            println(evaluate(toPostfix(toValues(toInfixList(input), varMaps))))
                        }
                        else -> println("Invalid expression")
                    }
                }

            } catch (e: Exception) {
                println("Invalid expression")
            }
        }
    }

    fun assign(input: String, maps: MutableMap<String, Int>) {
        val regexAssign = Regex("[a-zA-Z0-9]+|\\s*=\\s*|[-]?\\(?[a-zA-Z0-9]+")
        if (!input.contains('^') && !input.contains('*') &&
            !input.contains('/') && !input.contains('+') &&
            !input.contains('-')
        ) {
            val matches = regexAssign.findAll(input)
            val result = emptyList<String>().toMutableList()
            matches.forEach {
                result.add(it.value)
            }
            val res1 = result[0]
            val res2 = result[2]
            when {
                hasDigit(res1) && hasLetter(res1) -> println("Invalid identifier")
                (!hasDigit(res1) && hasLetter(res1)) && (hasDigit(res2) && hasLetter(res2)) -> println(
                    "Invalid assignment"
                )
                (!hasDigit(res1) && hasLetter(res1)) && (hasDigit(res2) && !hasLetter(res2)) -> {
                    maps[res1] = res2.toInt()
                }
                (!hasDigit(res1) && hasLetter(res1)) && (!hasDigit(res2) && hasLetter(res2)) -> {
                    if (res2.first() == '-') {
                        val unsigned = res2.substring(1 until res2.length)
                        if (unsigned in maps.keys) {
                            maps[res1] = -1 * (maps[res2] ?: 0)
                        }
                    } else {
                        if (res2 in maps.keys) {
                            maps[res1] = maps[res2] ?: 0
                        } else println("Unknown variable")
                    }
                }
                else -> println("Invalid expression")
            }
        } else {
            val invalidAssign = Regex("[a-zA-Z]+\\s*=\\s*\\d+\\s*[-+*/^]\\s*\\d+\\s*=\\s*\\d+")
            if (invalidAssign.matches(input)) println("Invalid assignment")
            val (first, second) = input.split("=")
            val res1 = removeSpace(first)
            val res2 = evaluate(toPostfix(toValues(toInfixList(second), maps)))
            maps[res1] = res2
        }
    }

    fun toInfixList(input: String): List<String> {
        val adjusted = emptyList<String>().toMutableList()
        val expSplitter = Regex("\\(|[-]?[a-zA-Z0-9]+|[\\s*]|[-+*^/]+|[)]")
        val matches = expSplitter.findAll(input)
        for (res in matches) {
            if (res.value.contains(' ')) continue
            else adjusted.add(res.value)
        }
        for (i in adjusted.indices) {
            if (!hasDigit(adjusted[i]) && !hasLetter(adjusted[i])) {
                if (adjusted[i].contains('+')) {
                    adjusted[i] = "+"
                } else if (adjusted[i].contains('-')) {
                    if (adjusted[i].length % 2 != 0) {
                        adjusted[i] = "-"
                    } else {
                        adjusted[i] = "+"
                    }
                }
            }
        }
        return adjusted
    }

    fun toValues(list: List<String>, maps: MutableMap<String, Int>): List<String> {
        val tempValues = emptyList<String>().toMutableList()
        for (i in list.indices) {
            if (hasLetter(list[i])) {
                if (list[i].first() == '-') {
                    val unsigned = list[i].substring(1 until list[i].length)
                    if (unsigned in maps.keys) {
                        tempValues.add(((-1 * (maps[unsigned] ?: 0)).toString()))
                    } else tempValues.add(list[i])
                } else {
                    if (list[i] in maps.keys) {
                        tempValues.add(((maps[list[i]] ?: 0).toString()))
                    } else tempValues.add(list[i])
                }
            } else tempValues.add(list[i])
        }
        return tempValues
    }

    fun toPostfix(infix: List<String>): List<String> {
        val regexVal = Regex("[-]?\\d+")
        val pre = mutableMapOf<String, Int>()
        pre["^"] = 3; pre["*"] = 2; pre["/"] = 2
        pre["+"] = 1; pre["-"] = 1
        val postfix = emptyList<String>().toMutableList()
        val stack = MutableStack<String>()
        for (element in infix) {
            if (regexVal.matches(element)) {
                postfix.add(element)
            } else {
                if (stack.isEmpty() || element == "(") {
                    stack.push(element)
                } else if (element == ")") {
                    while (stack.peek() != "(") {
                        postfix.add(stack.pop())
                    }
                    stack.pop()
                } else if ((pre[element] ?: 0) > (pre[stack.peek()] ?: 0)) {
                    stack.push(element)
                } else {
                    while (!stack.isEmpty() && (pre[element] ?: 0) <= (pre[stack.peek()] ?: 0)) {
                        postfix.add(stack.pop())
                    }
                    stack.push(element)
                }
            }
        }
        while (!stack.isEmpty()) {
            postfix.add(stack.pop())
        }
        return postfix
    }

    fun evaluate(postfix: List<String>): Int {
        val ops = "^*/+-"
        val stack = MutableStack<Int>()
        for (element in postfix)
            if (element in ops) {
                val right = stack.pop()
                val left = stack.pop()
                val res: Int = when (element) {
                    "^" -> left.toString().toDouble().pow(right).toInt()
                    "*" -> left * right
                    "/" -> try {
                        left / right
                    } catch (e: Exception) {
                        println("Invalid operation - division by zero")
                        break
                    }
                    "+" -> left + right
                    else -> left - right
                }
                stack.push(res.toString().toInt())
            } else {
                stack.push(element.toInt())
            }
        return stack.pop()
    }

    fun hasDigit(str: String): Boolean {
        var hasNumber = false
        for (i in str.indices) {
            if (str[i].isDigit()) {
                hasNumber = true
                break
            }
        }
        return hasNumber
    }

    fun hasLetter(str: String): Boolean {
        var isChar = false
        for (i in str.indices) {
            if (str[i].isLetter()) {
                isChar = true
                break
            }
        }
        return isChar
    }

    fun checkVar(input: String, map: MutableMap<String, Int>) {
        val checkVar = Regex("\\s*[-]?[a-zA-Z0-9]+\\s*")
        val match = checkVar.find(input)
        val tempVar = match?.value
        val variable = removeSpace(tempVar ?: "")
        when {
            hasDigit(variable ?: "") && !hasLetter(variable ?: "") -> println(variable.toInt())
            !hasDigit(variable ?: "") && hasLetter(variable ?: "") -> {
                if (variable in map.keys) {
                    if (variable.first() == '-') println(-1 * (map[variable] ?: 0))
                    else println(map[variable] ?: 0)

                } else println("Unknown variable")
            }
            else -> println("Invalid identifier")
        }
    }

    class MutableStack<E>(vararg items: E) {
        private val elements = items.toMutableList()
        fun push(element: E) = elements.add(element)
        fun pop(): E = elements.removeAt(elements.size - 1)
        fun peek(): E = elements.last()
        fun isEmpty() = elements.isEmpty()
        fun contains(element: E) = element in elements
    }

    fun removeSpace(str: String): String {
        return str.filter { it != ' ' }
    }
}