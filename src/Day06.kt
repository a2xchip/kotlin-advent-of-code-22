fun main() {
    fun findStartOfMessageMarker(stringSequence: String, size: Int): Int {
        stringSequence.toCharArray().toList().windowed(size).forEachIndexed { k, v ->
            if (v.toSet().size == size) return k + size
        }

        throw Exception(message = "Marker not found")
    }

    fun part1(input: List<String>): Int {
        return findStartOfMessageMarker(input.first(), 4)
    }

    fun part2(input: List<String>): Int {
        return findStartOfMessageMarker(input.first(), 14)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)

    val input = readInput("Day06")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 1896)
    check(part2(input) == 3452)
}

