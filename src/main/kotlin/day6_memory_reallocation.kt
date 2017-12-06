/**
 *
 * @author Holger Brandl
 */

fun main(args: Array<String>) {
    val input = "2 8 8 5 4 2 3 1 5 5 1 2 15 13 5 14".split(" ").map { it.toInt() }.toIntArray()

    //    val input = listOf(0, 2, 7, 0).toIntArray()


    tailrec fun checkCycles(memBanks: IntArray, stateHistory: MutableSet<String> = mutableSetOf(memBanks.joinToString())): Int {
        val maxBank = memBanks.indexOfFirst { it == memBanks.max() }

        val numBlocks = memBanks[maxBank]
        memBanks[maxBank] = 0

        for (idx in (maxBank + 1) until (maxBank + 1 + numBlocks)) {
            System.err.println("idx is ${idx % memBanks.size}")
            memBanks[idx % memBanks.size] += 1
        }

        val stateSnapshot = memBanks.joinToString()
        return if (stateHistory.contains(stateSnapshot)) {
            stateHistory.size
        } else {
            System.err.println("adding $stateSnapshot")
            stateHistory.add(stateSnapshot) //todo use for if
            checkCycles(memBanks, stateHistory)
        }
    }

    val numCycles = checkCycles(input)
    println("numCycles unti same bank config: $numCycles")
}