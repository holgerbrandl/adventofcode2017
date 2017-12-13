import org.jgrapht.alg.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File

/**
 * @author Holger Brandl
 */
fun main(args: Array<String>) {

    //    val data = File("day12_test_data.txt")
    val data = File("day12_data.txt")
        .readLines().map { it.split(" ", ", ") }
        .associate { it[0].toInt() to it.drop(2).map { it.toInt() } }

    val g = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)

    data.flatMap { it.value + it.value }.distinct().forEach { g.addVertex(it) }

    data.toMap().map { (from, tos) ->
        tos.forEach { to -> if (from != to) g.addEdge(from, to) }
    }

    val connectedSets = ConnectivityInspector(g).connectedSets()
    val componentSize = connectedSets.first { it.contains(0) }.size

    println("subgraph containing prog0 contained $componentSize elements")
    println("num connected components is ${connectedSets.size}")
}