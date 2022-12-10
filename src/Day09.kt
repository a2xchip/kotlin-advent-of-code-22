import kotlin.math.abs
import kotlin.math.sign

class RopeAction(val direction: Direction, val stepsCount: Int) {
    companion object {
        fun fromString(s: String): RopeAction {
            val (d, c) = s.split(" ")
            return RopeAction(Direction.valueOf(d), c.toInt())
        }
    }
}

enum class Direction() {
    R, L, U, D
}

class RopeLog {
    private val headLog = mutableListOf<Pair<Int, Int>>()
    private val tailLog = mutableListOf<Pair<Int, Int>>()
    fun tailUniquePositionsCount(): Int {
        return tailLog.toSet().size
    }

    fun recordHead(head: Head) {
        headLog.add(head.x to head.y)
    }

    fun recordTail(tail: Tail) {
        tailLog.add(tail.x to tail.y)
    }
}

open class Knot(var x: Int, var y: Int) {
    fun move(direction: Direction) {
        when (direction) {
            Direction.U -> y += 1
            Direction.D -> y -= 1
            Direction.R -> x += 1
            Direction.L -> x -= 1
        }
    }

    fun move(position: Pair<Int, Int>) {
        x = position.first
        y = position.second
    }
}

class Head(x: Int, y: Int) : Knot(x, y) {}

class Tail(x: Int, y: Int) : Knot(x, y) {
    fun followHead(head: Head) {
        if (listOf(abs(head.x - x), abs(head.y - y)).max() < 2) return // distance is 1 or less
        if (head.x == x) y += (head.y - y).sign // follow vertically
        else if (head.y == y) x += (head.x - x).sign // follow horizontally
        else { // diagonal adjustment
            x += (head.x - x).sign
            y += (head.y - y).sign
        }
    }
}

class KnotInteraction {
    private val head: Head = Head(0, 0)
    private val tail: Tail = Tail(0, 0)
    private val ropeLog: RopeLog = RopeLog()

    init {
        ropeLog.recordHead(head)
        ropeLog.recordTail(tail)
    }

    fun moveHead(action: RopeAction) {
        head.move(action.direction)
        ropeLog.recordHead(head)
        tail.followHead(head)
        ropeLog.recordTail(tail)
    }

    fun moveHead(position: Pair<Int, Int>) {
        head.move(position)
        ropeLog.recordHead(head)
        tail.followHead(head)
        ropeLog.recordTail(tail)
    }

    fun countTailsUniquePositions(): Int {
        return ropeLog.tailUniquePositionsCount()
    }

    fun tailPosition(): Pair<Int, Int> {
        return tail.x to tail.y
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val knotInteraction = KnotInteraction()

        for (m in input) {
            val action = RopeAction.fromString(m)
            repeat(action.stepsCount) {
                knotInteraction.moveHead(action)
            }
        }

        return knotInteraction.countTailsUniquePositions()
    }

    fun part2(input: List<String>): Int {
        val numberOfKnots = 9
        val knotInteractions = List(numberOfKnots) { KnotInteraction() }
        for (m in input) {
            val action = RopeAction.fromString(m)
            repeat(action.stepsCount) {
                for ((i, interaction) in knotInteractions.withIndex()) {
                    if (i == 0) {
                        interaction.moveHead(action)
                    } else {
                        val position = knotInteractions[i - 1].tailPosition()
                        interaction.moveHead(position)
                    }
                }
            }
        }

        return knotInteractions.last().countTailsUniquePositions()
    }

    val input = readInput("Day09")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 6339)
    check(part2(input) == 2541)
}
