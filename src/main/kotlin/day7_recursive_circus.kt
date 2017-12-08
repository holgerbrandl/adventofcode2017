import java.io.File

/**
 * @author Holger Brandl
 */


data class Tower(val name: String, val weight: Int, val above: List<String>) {


}

fun main(args: Array<String>) {

    //        val data = """
    //            pbga (66)
    //            xhth (57)
    //            ebii (61)
    //            havc (66)
    //            ktlj (57)
    //            fwft (72) -> ktlj, cntj, xhth
    //            qoyq (66)
    //            padx (45) -> pbga, havc, qoyq
    //            tknk (41) -> ugml, padx, fwft
    //            jptl (61)
    //            ugml (68) -> gyxo, ebii, jptl
    //            gyxo (61)
    //            cntj (57)
    //            """.trimIndent().trim().lines()


    val data = File("day7_data.txt").readLines()

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

    fun Tower.getParent() = towers.firstOrNull { it.above.contains(name) }
    fun List<Tower>.byName(name: String) = first { it.name == name }

    fun Tower.children() = above.map { towers.byName(it) }

    tailrec fun Tower.findRoot(towers: List<Tower>): Tower {
        val onTopOf = getParent()
        return if (onTopOf == null) this else onTopOf.findRoot(towers)
    }

    // part1: find the root of the tower of a single element
    val root = towers.first().findRoot(towers)
    println(root)


    // part2: find unbalanced program
    //find disc with unbalanced weight
    fun Tower.totalWeight(): Int = weight + children().map { it.totalWeight() }.sum()

    val unbalSubTree = towers.filter { it.children().map { it.totalWeight() }.distinct().size > 1 }

    //    for (t in unbalSubTree) {
    //        println("\nparent $t")
    //        println("children:")
    //        t.children().forEach { println(it.toString() + " -> " + it.totalWeight()) }
    //    }

    // select most specific tower
    val unbalTower = unbalSubTree.sortedBy { it.totalWeight() }.first()

    // reveal the specic tower pm \\
    val first = unbalTower.children().groupBy { it.totalWeight() }.toList().map { it.second }.let { disc ->
        val correctWeight = disc.first { it.size > 1 }.first().totalWeight()
        val faultyTower = disc.first { it.size == 1 }.first()

        val newWeight = faultyTower.weight - (faultyTower.totalWeight() - correctWeight)
        println("new weight ${newWeight}")
    }
}

