/**
 *
 * @author Holger Brandl
 */

fun main(args: Array<String>) {
    val input = "2 8 8 5 4 2 3 1 5 5 1 2 15 13 5 14".split(" ").map { it.toInt() }.toIntArray()
    //        val input = listOf(0, 2, 7, 0).toIntArray()


    tailrec fun checkCycles(memBanks: IntArray, stateHistory: MutableList<String> = mutableListOf()): Pair<Int, Int> {
        memBanks.hashCode()
        val stateSnapshot = memBanks.joinToString()

        return if (stateHistory.contains(stateSnapshot)) {
            val numInbetween = stateHistory.size - stateHistory.indexOf(stateSnapshot)
            stateHistory.size to numInbetween

        } else {
            val (maxBank, numBlocks) = memBanks.withIndex().maxBy { it.value }!!

            // reset bank
            memBanks[maxBank] = 0

            for (idx in (maxBank + 1) until (maxBank + 1 + numBlocks)) {
                memBanks[idx % memBanks.size] += 1
            }

            stateHistory += stateSnapshot
            checkCycles(memBanks, stateHistory)
        }
    }

    val cycleHistory = checkCycles(input)
    println("part 1: runs until same bank config: ${cycleHistory.first}")
    println("part 2: run between same bank config: ${cycleHistory.second}")
}