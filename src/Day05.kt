fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)
    println("Test 1 - ${part1(testInput)}")
    println("Test 2 - ${part2(testInput)}")

    val input = readInput("Day05")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 0)
    check(part2(input) == 0)
}

