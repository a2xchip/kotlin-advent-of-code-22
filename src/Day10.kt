fun main() {
    fun part1(input: List<String>): Int {
        val cycles = mutableListOf<Int>()
        val crt = MutableList(240) { Char(0) }
        var register = 1

        for (c in input) {
            cycles.add(register)
            if (c.startsWith("noop")) continue
            val value = c.split(" ").last().toInt()
            cycles.add(register)
            register += value
        }

        for ((k, value) in cycles.withIndex()) {
            val position = k % 240
            crt[position] = if (position % 40 in value - 1..value + 1) '$' else '.'
        }

        for (row in crt.chunked(40)) {
            for (c in row) {
                print(" $c ")
            }
            println()
        }

        return cycles.mapIndexed { k, value -> (k + 1) * value }.filterIndexed { k, _ -> (k + 1) % 40 == 20 }
            .sumOf { it }
    }

    val input = readInput("Day10")
    check(part1(input) == 10760)
}
