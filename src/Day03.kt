fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 0)

    val input = readInput("Day03")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}