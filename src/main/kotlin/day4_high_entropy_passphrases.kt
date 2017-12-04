import java.io.File

/**
 * @author Holger Brandl
 */
class Context

fun main(args: Array<String>) {
    val data = File("day4_data.txt").readLines()

    // part1

    // 1. A passphrase consists of a series of words (lowercase letters) separated by spaces.
    // 2. To ensure security, a valid passphrase must contain no duplicate words.
    fun isValid(passphrase: String) = with(passphrase.split(" ")) {
        all { it.matches("[a-z]*".toRegex()) } && distinct().size == size
    }

    println(data.count { isValid(it) })

    // part 2
    // additional rule: no anagrams
    fun isValidNoAnagram(passphrase: String) = isValid(passphrase) &&
        passphrase.split(" ").map { it.toCharArray().sorted() }.run { distinct().size == size }

    println(data.count { isValidNoAnagram(it) })


}