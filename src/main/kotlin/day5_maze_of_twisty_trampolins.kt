import java.io.File
import kotlin.coroutines.experimental.buildSequence


fun main(args: Array<String>) {

    // val data = listOf(0, 3, 0, 1, -3)
    val data = File("day5_data.txt").readLines().map { it.toInt() }

    val escapeSeq: Sequence<Int> = buildSequence {

        val maze = data.mapIndexed { index, instruction ->
            instruction
        }.toMutableList()

        var ptr = 0

        while (ptr in 0 until maze.size) {
            yield(ptr)

            val prevPtr = ptr
            ptr += maze[ptr]

            // part 1
            maze[prevPtr] += 1

            // part 2
            //            maze[prevPtr] += if (maze[prevPtr] >= 3) -1 else 1
        }
    }

    //    follow the jumps until one leads outside the list.
    println(escapeSeq.count())
}