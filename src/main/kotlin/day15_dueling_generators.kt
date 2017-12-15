/**
 * @author Holger Brandl
 */

fun main(args: Array<String>) {

    //    // puzzle input
    val startA = 883L
    val startB = 879L

    // test puzzle input
    //    val startA = 65L
    //    val startB = 8921L


    val genAFac = 16807
    val genBFac = 48271


    fun Long.lower16() = Integer.toBinaryString(toInt())
        .padStart(32, '0')
        .substring(16, 32)

    // part1
    fun generate(start: Long, factor: Int) = generateSequence(start) { (it * factor) % 2147483647 }.drop(1)

    val take = generate(startA, genAFac).zip(generate(startB, genBFac)).take(40_000_000)
    val numMatches = take.sumBy { (a, b) ->
        if (a.lower16() == b.lower16()) 1 else 0
    }

    println("part1 $numMatches")

    // part2

    fun generateP2(start: Long, factor: Int, multipleOf: Int) = generateSequence(start) {
        (it * factor % 2147483647)
    }.drop(1).filter {
        it % multipleOf == 0L
    }

    val zipped = generateP2(startA, genAFac, 4).zip(generateP2(startB, genBFac, 8)).take(5_000_000)

    val numP2Matches = zipped.sumBy { (genA, genB) ->
        if (genA.lower16() == genB.lower16()) 1 else 0
    }

    println("part2 $numP2Matches")
}

