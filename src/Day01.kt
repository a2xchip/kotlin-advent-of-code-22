import java.io.File

fun main() {
    fun readInput(name: String) = File("src", "$name.txt").readText().split("\n\n")

    fun perElf(input: List<String>): List<Int> = input.map { elf ->
        elf.split("\n").map { it.toInt() }.sum()
    }

    fun part1(input: List<String>): Int {
        return perElf(input).maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return perElf(input).sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}
