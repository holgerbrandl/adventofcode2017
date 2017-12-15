/**
 * @author Holger Brandl
 */

fun main(args: Array<String>) {

    //    // puzzle input
    //    var startA = 883L
    //    var startB = 879L

    // test puzzle input
    val startA = 65L
    val startB = 8921L


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
    //    val genDataA =

    //    fun generateP2(start: Long, factor: Int, multipleOf: Int) = generateSequence(start) {
    //        (it * factor % 2147483647).takeIf { it % multipleOf == 0L }
    //    }.take(5_000_000).filterNotNull()
    //
    //
    //    val zipped = generate(startA, genAFac, 4).zip(generate(startB, genBFac, 4)).toList()
    //
    //    val numP2Matches = zipped.sumBy { (genA, genB) ->
    //        if (genA.lower16() == genB.lower16()) 1 else 0
    //    }
    //
    //    println("part2 $numP2Matches")

}

