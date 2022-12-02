fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day02")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}