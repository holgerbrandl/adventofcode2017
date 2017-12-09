import java.io.File

/**
 * @author Holger Brandl
 */

//        val data = File("day8_test_data.txt").readLines()
val data = File("day8_data.txt").readLines()

val registers = mutableMapOf<String, Int>()

var maxValue = Integer.MIN_VALUE

data.map { it.split(" ") }.forEach { line ->
    // check the predicate
    val lhs = registers.getOrPut(line[4], { 0 })
    val rhs = line[6].toInt()

    val updateRegister = when (line[5]) {
        ">" -> lhs > rhs
        "<" -> lhs < rhs
        "!=" -> lhs != rhs
        "==" -> lhs == rhs
        "<=" -> lhs <= rhs
        ">=" -> lhs >= rhs
        else -> TODO()
    }

    if (!updateRegister) return@forEach

    val curRegValue = registers.getOrPut(line[0], { 0 })
    val someCrement = line[2].toInt()

    registers[line[0]] = when (line[1]) {
        "inc" -> curRegValue + someCrement
        "dec" -> curRegValue - someCrement
        else -> TODO()
    }

    // update max value for part2
    maxValue = listOf(maxValue, registers.maxBy { it.value }!!.value).max()!!
}

// p1
println(registers.maxBy { it.value })

//p2
println(maxValue)

