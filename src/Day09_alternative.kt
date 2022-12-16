import kotlin.math.abs
import kotlin.math.sign

class KnotChain(private val id: Int, private var head: KnotChain? = null, private var tail: KnotChain? = null) {
    private val log = mutableSetOf<Pair<Int, Int>>()
    private var x = 0
    private var y = 0

    override fun toString(): String {
        return "#$id - head: ${head?.id()}, tail: ${tail?.id()}, end: ${tail().id()}"
    }

    fun id(): Int = id

    fun addTail() {
        val currentTail = tail()
        currentTail.tail = KnotChain(id + 1, currentTail)
    }
    fun tail(): KnotChain {
        if (tail != null) {
            return tail!!.tail()
        }

        return this
    }

    fun move(direction: KnotDirection) {
        when (direction) {
            KnotDirection.U -> y += 1
            KnotDirection.D -> y -= 1
            KnotDirection.R -> x += 1
            KnotDirection.L -> x -= 1
        }

        updateLog()
        tail?.follow()
    }

    fun follow() {
        doFollow()
        updateLog()
        tail?.follow()
    }
    fun countTailsUniquePositions(): Int {
        return tail().logCount()
    }

    private fun logCount(): Int = log.size

    private fun doFollow() {
        if (listOf(abs(head!!.x - x), abs(head!!.y - y)).max() < 2) return
        if (head!!.x == x) y += (head!!.y - y).sign
        else if (head!!.y == y) x += (head!!.x - x).sign
        else {
            x += (head!!.x - x).sign
            y += (head!!.y - y).sign
        }
    }

    private fun updateLog() {
        log.add(x to y)
    }

    companion object {
        fun create(chainLength: Int = 1): KnotChain {
            val head = KnotChain(0)
            repeat(chainLength) {
                head.addTail()
            }

            return head
        }
    }
}

enum class KnotDirection() {
    R, L, U, D
}

fun main() {
    fun part1(input: List<String>): Int {
        val head = KnotChain.create()

        for (m in input) {
            val (direction, steps) = m.split(" ")
            repeat(steps.toInt()) {
                head.move(KnotDirection.valueOf(direction))
            }
        }

        return head.countTailsUniquePositions()
    }

    fun part2(input: List<String>): Int {
        val head = KnotChain.create(9)
        for (m in input) {
            val (direction, steps) = m.split(" ")
            repeat(steps.toInt()) {
                head.move(KnotDirection.valueOf(direction))
            }
        }

        return head.countTailsUniquePositions()
    }

    val input = readInput("Day09")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 6339)
    check(part2(input) == 2541)
}
