private fun String.toRange(): IntRange {
    val (from, to) = this.split("-").map { it.toInt() }
    return from..to
}

fun toRangePairs(line: String): Pair<IntRange, IntRange> {
    val (a, b) = line.split(",")
    return a.toRange() to b.toRange()
}

fun fullyOverlaps(pair: Pair<IntRange, IntRange>): Boolean {
    val (first, second) = pair
    return first.all(second::contains) || second.all(first::contains)
}

fun partiallyOverlaps(pair: Pair<IntRange, IntRange>): Boolean {
    val (first, second) = pair
    return first.any(second::contains) || second.any(first::contains)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map(::toRangePairs).filter(::fullyOverlaps).size
    }

    fun part2(input: List<String>): Int {
        return input.map(::toRangePairs).filter(::partiallyOverlaps).size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
    println("Test 1 - ${part1(testInput)}")
    println("Test 2 - ${part2(testInput)}")

    val input = readInput("Day04")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 471)
    check(part2(input) == 888)
}

