/**
 * @author Holger Brandl
 */

fun main(args: Array<String>) {
    val genAFac = 16807
    val genBFac = 48271

    val by = 2147483647

    //    // puzzle input
    var genA = 883L
    var genB = 879L

    // test puzzle input
    //    var genA = 65L
    //    var genB = 8921L


    val numMatches = (0 until 40_000_000).sumBy {
        genA = genA * genAFac % by
        genB = genB * genBFac % by

        if (genA.lower16() == genB.lower16()) 1 else 0
    }

    println("lower 16 matches $numMatches")

}

fun Long.lower16() = Integer.toBinaryString(toInt())
    .padStart(32, '0')
    .substring(16, 32)
