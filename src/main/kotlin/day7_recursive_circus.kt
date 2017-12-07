import java.io.File

/**
 * @author Holger Brandl
 */


data class Tower(val name: String, val weight: Int, val above: List<String>) {


}

fun main(args: Array<String>) {

    //    val data = """
    //        pbga (66)
    //        xhth (57)
    //        ebii (61)
    //        havc (66)
    //        ktlj (57)
    //        fwft (72) -> ktlj, cntj, xhth
    //        qoyq (66)
    //        padx (45) -> pbga, havc, qoyq
    //        tknk (41) -> ugml, padx, fwft
    //        jptl (61)
    //        ugml (68) -> gyxo, ebii, jptl
    //        gyxo (61)
    //        cntj (57)
    //        """.trimIndent().trim().lines()


    val data = File("day7_data.txt").readLines()

    tailrec fun Tower.findRoot(towers: List<Tower>): Tower {
        val onTopOf = towers.firstOrNull { it.above.contains(name) }
        return if (onTopOf == null) this else onTopOf.findRoot(towers)
    }

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

    // find the root of the tower of a single element
    println(towers.first().findRoot(towers))
}