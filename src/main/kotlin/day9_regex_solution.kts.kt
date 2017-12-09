/**
 * @author Karel Peeters (shared via slack)
 */

fun main(args: Array<String>) {
    val text = ""
    val cancelled = text.replace("!.".toRegex(), "")
    // reluctant regex modifier" indicated by the `?` in `<.*?>`
    // Normally `*` is greedy, meaning it takes as much of the input as possible, and that would be problematic in
    // a string like `"{<abc>}<abc>"`. Using `*?` makes it take as little of the input as possible
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