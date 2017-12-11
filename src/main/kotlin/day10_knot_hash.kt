import java.util.*

/**
 * @author Holger Brandl
 */


class CircularList<E> : ArrayList<E>() {

    override fun get(index: Int): E {
        return super.get(index % size)
    }

    override fun set(index: Int, element: E): E {
        return super.set(index % size, element)
    }
}


fun main(args: Array<String>) {
    // https://kotlinlang.org/docs/reference/operator-overloading.html

    val puzzleData = listOf(212, 254, 178, 237, 2, 0, 1, 54, 167, 92, 117, 125, 255, 61, 159, 164)
    val knotData = CircularList<Int>().apply { addAll(0..255) }
    //    val puzzleData = listOf(3, 4, 1, 5)
    //    val knotData = CircularList<Int>().apply { addAll(0..4) }


    var curPos = 0
    var skip = 0

    for (length in puzzleData) {
        // apply the knot
        for (halfWindow in 0 until (length / 2)) {
            val tmp = knotData[curPos + halfWindow]
            knotData[curPos + halfWindow] = knotData[curPos + length - halfWindow - 1]
            knotData[curPos + length - halfWindow - 1] = tmp
        }


        curPos = (curPos + length + skip) % knotData.size
        skip++
        println("state:${knotData}")
        print("")
    }

    println("product is ${knotData[0] * knotData[1]}")
}