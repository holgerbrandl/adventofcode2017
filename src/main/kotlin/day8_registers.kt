import java.io.File

/**
 * @author Holger Brandl
 */

fun main(args: Array<String>) {
    //    val data = File("day8_test_data.txt").readLines()
    val data = File("day8_data.txt").readLines()

    val registers = mutableMapOf<String, Int>()

    fun regValue(name: String) = registers.getOrPut(name, { 0 })

    data.map { it.split(" ") }.forEach { line ->
        // check the predicate
        val lhs = regValue(line[4])
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

        //        println("updating register with $line")

        val curRegValue = registers.getOrPut(line[0], { 0 })
        val someCrement = line[2].toInt()

        registers[line[0]] = when (line[1]) {
            "inc" -> curRegValue + someCrement
            "dec" -> curRegValue - someCrement
            else -> TODO()
        }

    }

    println(registers.maxBy { it.value })
}