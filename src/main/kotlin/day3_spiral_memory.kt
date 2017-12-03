import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {


    data class DataElement(val data: Int, val x: Int, val y: Int)
    data class Point(val x: Int, val y: Int)


    fun DataElement.next(grid: MutableList<DataElement>): DataElement {
        val context = listOf(
            Point(x - 1, y),
            Point(x, y - 1),
            Point(x + 1, y),
            Point(x, y + 1)
        )

        // make cyclic
        val circularized = context + context

        val predecPos = with(grid.last()) { circularized.indexOf(Point(x, y)) }
        val turnPos = circularized[predecPos + 1]

        return if (grid.firstOrNull { it.x == turnPos.x && it.y == turnPos.y } == null) {
            return DataElement(data + 1, turnPos.x, turnPos.y)
        } else {
            val forward = circularized[predecPos + 1]
            DataElement(data + 1, forward.x, forward.y)
        }
    }


    // build spiral grid (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/generate-sequence.html)
    val spiralSeq = buildSequence {
        val grid = listOf(DataElement(0, 0, 0)).toMutableList()
        yield(grid.first())

        var curEl = DataElement(0, 1, 0)

        while (true) {
            yield(curEl)
            curEl = curEl.next(grid)
            grid.add(curEl)
        }
    }

    // puzzle input
    val puzzleInput = 1024 // shoul be 31 steps
    //    val puzzleInput = 265149 // actual
    val take = spiralSeq.take(puzzleInput)
    val spiralMemory = take.toList()
    val numSteps = take.last().run { x + y }

    println(numSteps)


    //    fun DataElement.stepHome(): DataElement? {
    //        if (data == 0) return null
    //
    //        val neighbors: List<DataElement> = with(listOf(0, 1, -1)) {
    //            // build cartesian product to consider all neighbors
    //            flatMap { i -> map { j -> i to j } }.map { (i, j) ->
    //                spiralMemory.firstOrNull { it.x == i && it.y == j }
    //            }.filterNotNull()
    //        }
    //
    //        return neighbors.minBy { it.data }!!
    //    }

}

