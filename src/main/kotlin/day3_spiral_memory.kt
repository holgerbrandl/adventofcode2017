import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs


data class DataElement(val sectorSum: Int, val x: Int, val y: Int)
data class Point(val x: Int, val y: Int)


fun DataElement.next(grid: List<DataElement>): DataElement {
    val context = listOf(
        Point(x + 1, y),
        Point(x, y - 1),
        Point(x - 1, y),
        Point(x, y + 1)
    )

    // make cyclic
    val circularized = context + context

    val predecessorPos = with(grid.last()) { circularized.indexOf(Point(x, y)) }
    val turn = circularized[predecessorPos + 1]
    val forward = circularized[predecessorPos + 2]

    fun List<DataElement>.byPosition(p: Point) = firstOrNull { it.x == p.x && it.y == p.y }

    // can we turn left? if not go straight in grid
    val newPos: Point = if (grid.byPosition(turn) == null) turn else forward

    val neighborSum = (with(listOf(0, 1, -1)) {
        flatMap { xOffset -> map { yOffset -> Point(newPos.x + xOffset, newPos.y + yOffset) } }
    }.map { grid.byPosition(it) }.filterNotNull() + this).sumBy { it.sectorSum }


    return DataElement(neighborSum, newPos.x, newPos.y)
}


val spiralSeq = buildSequence {
    val grid = listOf(
        DataElement(1, 0, 0),
        DataElement(1, 1, 0)
    ).toMutableList()

    yield(grid.first())

    var curEl = grid.last()

    while (true) {
        yield(curEl)

        if (grid.size.rem(10000) == 0) println("expanding grid to ${grid.size}")

        curEl = curEl.next(grid.dropLast(1))
        grid.add(curEl)
    }
}

fun main(args: Array<String>) {

    //    val puzzleInput = 14 // shoul be 122 node sum
    //    val puzzleInput = 1024 // shoul be 31 steps
    val puzzleInput = 265149 // actual
    val seq = spiralSeq.drop(puzzleInput - 1)


    // part 1
    // shortest manhattan path from puzzle to origin
    val puzzleNode = seq.first()
    println("num manhattan steps" + with(puzzleNode) { abs(x) + abs(y) })

    // part2
    // data for the node after the puzzle input
    val afterPuzzleNode = seq.drop(1).first()
    println("next puzzle sum: " + afterPuzzleNode.sectorSum)
}

