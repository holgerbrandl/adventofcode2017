/**
 * @author Holger Brandl
 */

fun main(args: Array<String>) {
    val text = ""
    val cancelled = text.replace("!.".toRegex(), "")
    val degarbaged = cancelled.replace("<.*?>".toRegex(), "<>")
    val minimal = degarbaged.replace(",|<>".toRegex(), "")

    var sum = 0
    var depth = 1
    for (c in minimal) {
        when (c) {
            '{' -> sum += depth++
            '}' -> depth--
        }
    }

    println("score: $sum")
    println("garbage size: ${cancelled.length - degarbaged.length}")
}