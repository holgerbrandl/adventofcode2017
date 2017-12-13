import java.io.File
import kotlin.math.abs
import kotlin.math.max

/**
 * @author Holger Brandl
 */


// https://www.redblobgames.com/grids/hexagons/
fun main(args: Array<String>) {

    require(traverseHexGrid("ne,ne,ne".split(",")).first == 3)
    require(traverseHexGrid("ne,ne,sw,sw".split(",")).first == 0)
    require(traverseHexGrid("ne,ne,s,s".split(",")).first == 2)
    require(traverseHexGrid("se,sw,se,sw,sw".split(",")).first == 3)

    val input = File("day11_data.txt").readText().split(",")
    val result = traverseHexGrid(input)
    println("part1 ${result.first}")
    println("part2 ${result.second}")

}


data class CubeCoord(val x: Int, val y: Int, val z: Int)

fun cubeDist(a: CubeCoord, b: CubeCoord) = (abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)) / 2

data class HexCoord(val col: Int, val row: Int) {
    fun move(colOffset: Int, rowOffset: Int) = copy(col + colOffset, row + rowOffset)

    fun toCube(): CubeCoord {
        val x = col
        val z = row - (col - abs(col % 2)) / 2
        val y = -x - z
        return CubeCoord(x, y, z)
    }

    fun distFromOrigin() = cubeDist(toCube(), HexCoord(0, 0).toCube())
}

fun traverseHexGrid(input: List<String>): Pair<Int, Int> {
    // use “odd-q” vertical layout
    var furthest = Int.MIN_VALUE

    val finalPos = input.fold(HexCoord(0, 0), { curHex, left ->
        val northOffset = abs((curHex.col + 1) % 2)
        val southOffset = abs(curHex.col % 2)

        when (left) {
            "nw" -> curHex.move(-1, -northOffset)
            "n" -> curHex.move(0, -1)
            "ne" -> curHex.move(+1, -northOffset)
            "se" -> curHex.move(1, southOffset)
            "s" -> curHex.move(0, 1)
            "sw" -> curHex.move(-1, southOffset)
            else -> TODO()
        }.apply {
            furthest = max(furthest, distFromOrigin())
        }
    })

    return finalPos.distFromOrigin() to furthest
}
