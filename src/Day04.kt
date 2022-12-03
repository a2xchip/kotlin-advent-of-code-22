fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 0)
    println("Test ${part1(testInput)}")

    val input = readInput("Day04")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 0)
    check(part2(input) == 0)
}