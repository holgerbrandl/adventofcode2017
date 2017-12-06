fun main(args: Array<String>) {

    val input = listOf(1, 2, 3).toIntArray()


    tailrec fun jump(offsets: IntArray, mutator: (Int) -> Int, pc: Int = 0, steps: Int = 0): Int =
        if (pc !in (0 until offsets.size)) steps
        else {
            val nextPc = pc + offsets[pc]
            offsets[pc] += mutator(offsets[pc])
            jump(offsets, mutator, nextPc, steps.inc())
        }

    // from Todd Ginsberg via slack
    fun solvePart1(): Int = jump(input, { 1 })

    fun solvePart2(): Int = jump(input, { if (it >= 3) -1 else 1 })

}