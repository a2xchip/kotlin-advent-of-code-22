private fun String.toSetOfItemsInside() = this.toCharArray().toSet()

private fun List<Set<Char>>.findCommonItem() = this.fold(this.first()) { acc, chars -> acc.intersect(chars) }.first()

private fun Char.calculatePriority() = if (this.isUpperCase()) {
    this - 'A' + 27
} else {
    this - 'a' + 1
}

fun main() {
    fun part1(input: List<String>): Int {
        // The first rucksack contains the items vJrwpWtwJgWrhcsFMMfFFhFp, which means its first compartment contains the
        // items vJrwpWtwJgWr, while the second compartment contains the items hcsFMMfFFhFp.
        // The only item type that appears in both compartments is lowercase p.
        // The second rucksack's compartments contain jqHRNqRjqzjGDLGL and rsFMfFZSrLrFZsSL.
        // The only item type that appears in both compartments is uppercase L.
        // The third rucksack's compartments contain PmmdzqPrV and vPwwTWBwg;
        // the only common item type is uppercase P.
        // The fourth rucksack's compartments only share item type v.
        // The fifth rucksack's compartments only share item type t.
        // The sixth rucksack's compartments only share item type s.
        return input.sumOf { line ->
            val itemsInCompartments = line.chunked(line.length / 2).map { it.toSetOfItemsInside() }
            itemsInCompartments.findCommonItem().calculatePriority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toSetOfItemsInside() }.chunked(3).sumOf { triplet ->
            triplet.findCommonItem().calculatePriority()
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    println("Test ${part1(testInput)}")

    val input = readInput("Day03")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 7446)
    check(part2(input) == 2646)
}