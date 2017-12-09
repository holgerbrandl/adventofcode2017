import java.io.File

/**
 * @author Holger Brandl
 */


fun main(args: Array<String>) {
    val input = File("day9_data.txt").readText()
    //    val input = "{{<ab>},{<ab>},{<ab>},{<ab>}}"
    //    val input = "{{<a!>},{<a!>},{<a!>},{<ab>}}"

    val inputIterator = input.toCharArray().iterator().apply { nextChar() }

    val totalScore = processGroup(inputIterator, 1)

    println("total score is $totalScore")
    println("amount ofgarbage in data $garbageCounter")
}

private fun processGroup(inputIterator: CharIterator, level: Int): Int {
    var groupScore = level

    while (inputIterator.hasNext()) {
        groupScore += when (inputIterator.nextChar()) {
            ',' -> 0
            '!' -> {
                inputIterator.nextChar(); 0
            }
            '{' -> processGroup(inputIterator, level + 1)
            '<' -> processGarbage(inputIterator)
            '}' -> return groupScore
            else -> 0
        }
    }

    // should happens just once if all input is processed
    return groupScore
}

var garbageCounter = 0

fun processGarbage(inputIterator: CharIterator): Int {
    while (true) {
        when (inputIterator.nextChar()) {
            '!' -> inputIterator.nextChar()
            '>' -> return 0
            else -> {
                garbageCounter++
            }
        }
    }
}