// Running a cinema theatre is no easy business. To help our friends, let's add statistics to your program.
// The stats will show the current income, total income, the number of available seats, and the percentage of occupancy.
//
// In addition, our friends asked you to take care of a small inconvenience:
// it's not good when a user can buy a ticket that has already been purchased by another user. Let's fix this!
//
// Objectives
// Now your menu should look like this:
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// When the item Statistics is chosen, your program should print the following information:
//
// The number of purchased tickets;
// The number of purchased tickets represented as a percentage. Percentages should be rounded to 2 decimal places;
// Current income;
// The total income that shows how much money the theatre will get if all the tickets are sold.
// The rest of the menu items should work the same way as before, except the item Buy a ticket
// shouldn't allow a user to buy a ticket that has already been purchased.
//
// If a user chooses an already taken seat, print That ticket has already been purchased!
// and ask them to enter different seat coordinates until they pick an available seat.
// Of course, you shouldn't allow coordinates that are out of bounds. If this happens, print Wrong input! and ask to enter different seat coordinates until the user picks an available seat.
//
// Examples
// The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.
//
// Enter the number of rows:
// > 6
// Enter the number of seats in each row:
// > 6
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 3
//
// Number of purchased tickets: 0
// Percentage: 0.00%
// Current income: $0
// Total income: $360
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 2
//
// Enter a row number:
// > 1
// Enter a seat number in that row:
// > 1
//
// Ticket price: $10
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 3
//
// Number of purchased tickets: 1
// Percentage: 2.78%
// Current income: $10
// Total income: $360
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 2
//
// Enter a row number:
// > 1
// Enter a seat number in that row:
// > 1
//
// That ticket has already been purchased!
//
// Enter a row number:
// > 10
// Enter a seat number in that row:
// > 20
//
// Wrong input!
//
// Enter a row number:
// > 4
// Enter a seat number in that row:
// > 4
//
// Ticket price: $10
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 1
//
// Cinema:
// 1 2 3 4 5 6
// 1 B S S S S S
// 2 S S S S S S
// 3 S S S S S S
// 4 S S S B S S
// 5 S S S S S S
// 6 S S S S S S
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 3
//
// Number of purchased tickets: 2
// Percentage: 5.56%
// Current income: $20
// Total income: $360
//
// 1. Show the seats
// 2. Buy a ticket
// 3. Statistics
// 0. Exit
// > 0

package cinema


object Cinema {

    private const val empty = 'S'
    private const val ticketBuyAble = 'B'
    private var rowsNum = 0
    private var seatsNum = 0
    private var numberOfSeats = 0
    private var numberPurchasedTickets = 0
    private var currentIncome = 0
    private lateinit var cinemaMap: Array<CharArray>

    @JvmStatic
    fun main(args: Array<String>) {
        println("Enter the number of rows:")
        rowsNum = readLine()!!.toInt()
        println("Enter the number of seats in each row:")
        seatsNum = readLine()!!.toInt()
        numberOfSeats = rowsNum * seatsNum
        cinemaMap = initCinemaMap(rowsNum, seatsNum)
        var menuNum = 5
        while (menuNum != 0) {
            println("""
    1. Show the seats
    2. Buy a ticket
    3. Statistics
    0. Exit
    """.trimIndent())
            menuNum = readLine()!!.toInt()
            if (menuNum == 1) {
                cinemaPrintMap(cinemaMap)
            }
            if (menuNum == 2) {
                buyTicketPrice(cinemaMap)
                numberPurchasedTickets++
            }
            if (menuNum == 3) {
                showStats()
            }
            println()
        }
    }

    private fun initCinemaMap(rowNum: Int, seatNum: Int): Array<CharArray> { //создаем поле для игры
        val cinemaMap = Array(rowNum) { CharArray(seatNum) }
        for (i in 0 until rowNum) {
            for (j in 0 until seatNum) {
                cinemaMap[i][j] = empty
            }
        }
        return cinemaMap
    }

    private fun cinemaPrintMap(arr: Array<CharArray>) {
        println("Cinema:")
        print("  ")
        for (i in 0 until arr[0].size) {
            print((i + 1).toString() + " ") //  добавляем строку координат X
        }
        println()
        for (i in arr.indices) {
            print((i + 1).toString() + " ") //  добавляем строку координат Y
            for (j in 0 until arr[0].size) { // печать map
                print(arr[i][j].toString() + " ")
            }
            print(System.lineSeparator())
        }
        print(System.lineSeparator())
    }

    private fun buyTicketPrice(cinemaMap: Array<CharArray>) {
        var value = 0
        while (value == 0) {
            println("Enter a row number:")
            val row = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            val seat = readLine()!!.toInt()
            if (row > rowsNum || seat > seatsNum) {
                println("Wrong input!")
                continue
            }
            if (cinemaMap[row - 1][seat - 1] == ticketBuyAble) {
                println("That ticket has already been purchased!")
                continue
            }
            value = if (numberOfSeats < 60 || row <= rowsNum / 2) {
                10
            } else {
                8
            }
            cinemaMap[row - 1][seat - 1] = ticketBuyAble
            println("Ticket price: $$value")
            currentIncome += value
        }
    }

    private fun showStats() {
        println("Number of purchased tickets: $numberPurchasedTickets")
        println(String.format("Percentage: %.2f", numberPurchasedTickets / numberOfSeats.toDouble() * 100) + "%")
        println("Current income: $$currentIncome")
        val totalIncome: Int = if (numberOfSeats < 60) {
            numberOfSeats * 10
        } else {
            rowsNum / 2 * seatsNum * 10 + (rowsNum - rowsNum / 2) * seatsNum * 8
        }
        println("Total income: $$totalIncome")
    }
}
