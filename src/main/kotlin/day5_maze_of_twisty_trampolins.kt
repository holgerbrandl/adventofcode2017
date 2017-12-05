import java.io.File
import kotlin.coroutines.experimental.buildSequence

/**
 * @author Holger Brandl
 */

var isPart1 = true // lazy hack to avoid more evolved maze builder

data class MazeEl(val position: Int, var offset: Int) {

    fun increment() {
        if (isPart1) {
            offset++
        } else {
            if (offset > 2) {
                offset--
            } else {
                offset++
            }
        }
    }

    val target: Int
        get() = position + offset
}

fun main(args: Array<String>) {
    //    val data = listOf(0, 3, 0, 1, -3)
    val data = File("day5_data.txt").readLines().map { it.toInt() }

    val theMaze: Sequence<MazeEl> = buildSequence {
        val maze = data.mapIndexed { index, instruction ->
            MazeEl(index, instruction)
        }

        val mazeDim = 0 until maze.size
        var cur = maze[0]

        while (cur.target in mazeDim) {
            val next = maze[cur.target]
            cur.increment()
            yield(next)
            cur = next
        }
    }


    //    follow the jumps until one leads outside the list.
    println("part1: " + (theMaze.count() + 1))

    isPart1 = false
    println("part1: " + (theMaze.count() + 1))
}