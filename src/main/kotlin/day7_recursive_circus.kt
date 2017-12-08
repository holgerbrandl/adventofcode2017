import java.io.File

/**
 * @author Holger Brandl
 */


data class Tower(val name: String, val weight: Int, val above: List<String>) {


}

fun main(args: Array<String>) {
    val data = File("day7_data.txt").readLines()
    //    val data = File("day7_test_data.txt").readLines()

    // parse the data
    val towers = data.map {
        it.split(" ").run {
            Tower(
                first(),
                get(1).trim('(', ')').toInt(),
                if (size > 3) drop(3).map { it.trim(',') } else emptyList()
            )
        }
    }

    fun Tower.getDisc() = towers.firstOrNull { it.above.contains(name) }
    fun Tower.children() = above.map { tName -> towers.first { it.name == tName } }


    // part1: find the root of the tower of a single element
    tailrec fun Tower.findRoot(towers: List<Tower>): Tower {
        val onTopOf = getDisc()
        return if (onTopOf == null) this else onTopOf.findRoot(towers)
    }

    val root = towers.first().findRoot(towers).also { println(it) }


    // part2: find unbalanced program
    fun Tower.totalWeight(): Int = weight + children().map { it.totalWeight() }.sum()


    // select most unbalanced disc
    val unbalancedDisc = towers
        .filter { it.children().map { it.totalWeight() }.distinct().size > 1 }
        .sortedBy { it.totalWeight() }.first()

    // calculate correct weight
    unbalancedDisc.children().groupBy { it.totalWeight() }.toList().map { it.second }.let { disc ->
        val correctWeight = disc.first { it.size > 1 }.first().totalWeight()
        val faultyChild = disc.first { it.size == 1 }.first()

        val newWeight = faultyChild.weight - (faultyChild.totalWeight() - correctWeight)
        println("new weight ${newWeight}")
    }
}

