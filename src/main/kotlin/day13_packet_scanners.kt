import java.io.File

/**
 * @author Holger Brandl
 */

data class Scanner(val range: Int, var state: Int = 0, var isForward: Boolean = true) {

    fun move() = apply {
        state += if (isForward) +1 else -1

        if (state == (range - 1)) isForward = false
        if (state == 0) isForward = true
    }
}


fun buildFirewall(): Map<Int, Scanner> {
    //            val firewall = File("day13_test_data.txt")
    val firewall = File("day13_data.txt")
        .readLines()
        .map { it.split(": ").map(String::toInt) }
        .associate { it[0] to it[1] }

    return firewall.map { it.key to Scanner(it.value) }.toMap()
}

val Map<Int, Scanner>.numLayers
    get() = map { it.key }.max()!!


fun main(args: Array<String>) {
    // part1
    var firewall = buildFirewall()

    val tripSeverity = (0..firewall.numLayers).map { pos ->
        firewall.get(pos)?.run {
            if (state == 0) pos * range else null
        }.also {
            firewall.values.forEach { it.move() }
        }
    }.filterNotNull().sum()

    println("the severity of the trip was $tripSeverity")

    // part2
    firewall = buildFirewall()

    fun isSneaky(firewall: Map<Int, Scanner>): Boolean = (0..firewall.numLayers).none { pos ->
        val wasCaught = firewall.get(pos)?.run {  state == 0 } ?: false
        firewall.values.forEach { it.move() }
        wasCaught
    }


    val minDelay = generateSequence(0) { it + 1 }.first { delay ->
        val fwClone = firewall.map { (idx, scanner) -> idx to scanner.copy() }.toMap()

        isSneaky(fwClone).also {
            firewall.values.forEach { it.move() }
        }
    }

    println("the min delay for sneak-through was $minDelay")
}
