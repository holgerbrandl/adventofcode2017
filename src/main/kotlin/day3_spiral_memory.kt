import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs


data class DataElement(val data: Int, val x: Int, val y: Int)
data class Point(val x: Int, val y: Int)


fun main(args: Array<String>) {

    fun DataElement.next(grid: List<DataElement>): DataElement {
        val context = listOf(
            Point(x + 1, y),
            Point(x, y - 1),
            Point(x - 1, y),
            Point(x, y + 1)
        )

        // make cyclic
        val circularized = context + context

        val lastEl = grid.last()
        val predecPos = with(lastEl) { circularized.indexOf(Point(x, y)) }
        val turn = circularized[predecPos + 1]
        val forward = circularized[predecPos + 2]

        // can we turn left, if not go straight in grid
        return if (grid.firstOrNull { it.x == turn.x && it.y == turn.y } == null) {
            return DataElement(data + 1, turn.x, turn.y)
        } else {
            DataElement(data + 1, forward.x, forward.y)
        }
    }


    // build spiral grid (see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/generate-sequence.html)
    val spiralSeq = buildSequence {
        val grid = listOf(DataElement(0, 0, 0), DataElement(1, 1, 0)).toMutableList()
        yield(grid.first())

        var curEl = grid.last()

        while (true) {
            yield(curEl)
            if (grid.size.rem(10000) == 0) println("expanding grid to ${grid.size}")
            curEl = curEl.next(grid.dropLast(1))
            grid.add(curEl)
        }
    }

    // puzzle input
    //    val puzzleInput = 1024 // shoul be 31 steps
    val puzzleInput = 265149 // actual
    val take = spiralSeq.take(puzzleInput)
    val numSteps = take.last().run { abs(x) + abs(y) }

    println(numSteps)
}

