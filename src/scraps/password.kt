package scraps

class password {
}

//The password is hard to crack if it contains at least A uppercase letters, at least B lowercase letters,
// at least C digits, and includes exactly N symbols.
// Also, a password cannot contain two or more same characters coming one after another.
// For a given numbers A, B, C, N you should output a password that matches these requirements.
//It is guaranteed A, B, C, and N are non-negative integers and A + B + C <= N.
// Keep in mind, that any parameter can be equal to zero.
// It means that it's ok if the password doesn't contain symbols of such type.

fun main() {
    println(chars(readLine()!!.split(' ').map { it.toInt() }.toList()))
}

fun chars(input: List<Int>): String {

    var a = input[0]
    var b = input[1]
    var c = input[2]
    var d = input[3]

    val upperCase = ('A'..'Z').toList()
    val lowerCase = ('a'..'z').toList()
    val digits = ('0'..'9').toList()
    val remainder = ('0'..'z').toList()

    var password = ""
    while (true) {
        if (a >= upperCase.size && input[0] > upperCase.size && input[3] - password.length >= a) {
            password += upperCase.joinToString("")
            a -= upperCase.size
            continue
        }
        if (a > 0) {
            password += upperCase.take(a).joinToString("")
            a = 0
        }
        if (b >= lowerCase.size && input[1] > lowerCase.size && input[3] - password.length >= b) {
            password += lowerCase.joinToString("")
            b -= lowerCase.size
            continue
        }
        if (b > 0) {
            password += lowerCase.take(b).joinToString("")
            b = 0
        }
        if (c >= digits.size && input[2] > digits.size && input[3] - password.length >= c) {
            password += digits.joinToString("")
            c -= digits.size
            continue
        }
        if (c > 0) {
            password += digits.take(c).joinToString("")
            c = 0
        }
        if (d - password.length >= remainder.size) {
            password += "x" + remainder.joinToString("")
            d -= remainder.size
            continue
        }
        d = input[3] - password.length
        if (d > 0) {
            password += "x" + remainder.take(d - 1).joinToString("")
            d = 0
        }
        if (password.length >= input[3]) {
            break
        }
    }
    return password
}