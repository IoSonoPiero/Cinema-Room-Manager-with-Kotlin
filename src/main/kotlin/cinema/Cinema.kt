package cinema

fun main() {
    // Get number of rows
    println("Enter the number of rows:")
    val rows: Int = readln().toInt()

    // Get number of seats in each row
    println("Enter the number of seats in each row:")
    val seatsPerRow: Int = readln().toInt()

    // Initialize the cinema
    val cinema = creteCinema(rows, seatsPerRow)

    // loop until choice is 0
    var choice: Int = 1
    do {
        printMenu()
        choice = getChoice()
        when (choice) {
            // Print cinema
            1 -> printCinema(cinema, rows, seatsPerRow)
            // Buy a ticket
            2 -> buyTicket(rows, seatsPerRow, cinema)
            // Show statistics
            3 -> showStatistics(rows, seatsPerRow, cinema)
            else -> println("\nWrong input!")
        }
    } while (choice != 0)
}

fun showStatistics(rows: Int, seatsPerRow: Int, cinema: MutableList<MutableList<Char>>) {
    var purchasedTickets: Int = 0
    var currentIncome: Int = 0
    val totalSeats: Int = rows * seatsPerRow

    // Iterate into cinema to get: purchased tickets AND current Income
    for (x in 0 until rows) {
        for (y in 0 until seatsPerRow) {
            // if seat is booked
            if (cinema[x][y] == 'B') {
                // increment purchased tickets
                purchasedTickets++

                // get the price of the seat
                currentIncome += calculateSeatPrice(rows, seatsPerRow, x + 1)
            }
        }
    }

    // Print number of purchased tickets
    println("\nNumber of purchased tickets: $purchasedTickets")

    // Print number of purchased tickets in percentage
    val percentage = purchasedTickets.toDouble() / totalSeats.toDouble() * 100.0
    val formatPercentage = "%.2f".format(percentage)
    println("Percentage: $formatPercentage%")

    // Current Income
    println("Current income: \$$currentIncome")

    // Total Income
    val totalIncome = getTotalIncome(rows, seatsPerRow)
    println("Total income: \$$totalIncome")

}

// Create bi-dimensional Mutable list of size rows * seatsPerRow
// Initialize it with 'S'
fun creteCinema(rows: Int, seatsPerRow: Int) = MutableList(rows) {
    MutableList(seatsPerRow) { 'S' }
}

fun buyTicket(rows: Int, seatsPerRow: Int, cinema: MutableList<MutableList<Char>>) {
    var isPurchasable: Boolean = false
    var isAlreadyBooked: Boolean = true
    var bookSeatRow: Int
    var bookSeatColumn: Int

    do {
        // Get the row of the seat to book
        println("\nEnter a row number:")
        bookSeatRow = readln().toInt()

        // Get the seat number in that row to book
        println("Enter a seat number in that row:")
        bookSeatColumn = readln().toInt()

        isPurchasable = checkIfPurchasable(bookSeatRow, rows, bookSeatColumn, seatsPerRow)
        if (isPurchasable) {
            isAlreadyBooked = checkIfAlreadyBooked(cinema, bookSeatRow, bookSeatColumn)
        }

    } while (!isPurchasable || !isAlreadyBooked)

    // Calculate the ticket price for the seat
    val ticketPrice: Int = calculateSeatPrice(rows, seatsPerRow, bookSeatRow)
    println("\nTicket price: \$$ticketPrice")

    // Book the seat
    cinema[bookSeatRow - 1][bookSeatColumn - 1] = 'B'
}

fun checkIfPurchasable(bookSeatRow: Int, rows: Int, bookSeatColumn: Int, seatsPerRow: Int): Boolean {
    // Check if the ticket is purchasable
    // Is the seat in the right range?
    return if (
        bookSeatRow > rows ||
        bookSeatRow <= 0 ||
        bookSeatColumn > seatsPerRow ||
        bookSeatColumn <= 0
    ) {
        println("\nWrong input!")
        false
    } else {
        true
    }
}

fun checkIfAlreadyBooked(cinema: MutableList<MutableList<Char>>, bookSeatRow: Int, bookSeatColumn: Int): Boolean {
    // Is the ticket already booked?
    return if (cinema[bookSeatRow - 1][bookSeatColumn - 1] == 'B') {
        println("\nThat ticket has already been purchased!")
        false
    } else {
        true
    }
}

fun getChoice() = readln().toInt()

fun printMenu() {
    println("\n1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun calculateSeatPrice(rows: Int, seatsPerRow: Int, bookSeatRow: Int): Int {
    return if (rows * seatsPerRow <= 60) 10
    else if (bookSeatRow > (rows / 2)) 8
    else 10
}

fun printCinema(cinema: MutableList<MutableList<Char>>, rows: Int, seatsPerRow: Int) {
    println("\nCinema:")
    print("  ")
    for (seat in 1..seatsPerRow) {
        print("$seat ")
    }
    println()
    for (row in 0 until rows) {
        print("${row + 1} ")
        println(cinema[row].joinToString(" "))
    }
}

fun getTotalIncome(rows: Int, seatsPerRow: Int): Int {
    val totalIncome: Int = if (rows * seatsPerRow <= 60) {
        (10 * rows * seatsPerRow)
    } else {
        val frontRows: Int = rows / 2
        val backRows: Int = rows / 2 + rows % 2
        ((10 * frontRows * seatsPerRow) + (8 * backRows * seatsPerRow))
    }
    return totalIncome
}