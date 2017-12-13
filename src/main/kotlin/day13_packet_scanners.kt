import java.io.File

/**
 * @author Holger Brandl
 */

class Scanner(val range: Int) {
    var state = 0
    var isForward = true

    fun move() {
        state += if (isForward) +1 else -1

        if (state == (range - 1)) isForward = false
        if (state == 0) isForward = true
    }

    override fun toString(): String {
        return "Scanner($range, $state)"
    }
}

fun main(args: Array<String>) {

//        val firewall = File("day13_test_data.txt")
    val firewall = File("day13_data.txt")
        .readLines()
        .map { it.split(": ").map(String::toInt) }
        .associate { it[0] to it[1] }


    val numLayers = firewall.map { it.key }.max()!!
    val scanners = firewall.map { it.key to Scanner(it.value) }.toMap()

    val sevScores = (0..numLayers).map { pos ->
        scanners.get(pos)?.run {
            if (state == 0) pos * range else null
        }.also {
            // move all scanners forward
            println(scanners.entries)

            scanners.values.forEach { it.move() }

        }
    }
    val tripSeverity = sevScores.filterNotNull().sum()

    println("the severity of the trip was $tripSeverity")
}