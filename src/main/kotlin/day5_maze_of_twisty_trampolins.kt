import java.io.File
import kotlin.coroutines.experimental.buildSequence

/**
 * @author Holger Brandl
 */

data class Jump(val offset: Int, var instruction: Int) {
    fun increment() {
        instruction++
    }

    val target: Int
        get() = offset + instruction
}

fun main(args: Array<String>) {
    val data = File("day5_data.txt").readLines().map { it.toInt() }
    //    val data = listOf(0,3,0,1,-3)
    //    val data = listOf(-3)

    val jumpSeq: Sequence<Jump> = buildSequence {
        val maze = data.mapIndexed { index, instruction ->
            Jump(index, instruction)
        }

        val mazeDim = 0 until maze.size
        var cur = maze[0]


        while (cur.target in mazeDim) {
            val next = maze[cur.target]
            cur.increment()
            yield(next)
            cur = next
            println(cur)
        }
    }


    //    follow the jumps until one leads outside the list.
    println(jumpSeq.count() + 1)

    // todo visualize the maze trip with kravis

}