fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 1)
    check(part2(input) == 1)
}
