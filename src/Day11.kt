import java.io.File

class Monkey(
    val items: MutableList<Long>,
    val worryLevelOperation: String,
    val worryLevelOperationValue: String,
    val testDivisibleBy: Int,
    val ifTrueThrowToMonkey: Int,
    val ifFalseThrowToMonkey: Int,
    var inspected: Int
) {
    companion object {
        fun from(monkeyStringBlock: String): Monkey {
            val seq = monkeyStringBlock.split("\n")
            val items = seq[1].split(":").last().trim().split(",").map { it.trim().toLong() }
            val (_, opOperation, opValue) = seq[2].split("=").last().trim().split(" ")
            val testDivisible = seq[3].split(" ").last().toInt()
            val ifTrue = seq[4].split(" ").last().trim().toInt()
            val ifFalse = seq[5].split(" ").last().trim().toInt()

            return Monkey(
                items.toMutableList(), opOperation, opValue, testDivisible, ifTrue, ifFalse, 0
            )
        }
    }
}

class Monkeys(val list: Array<Monkey>) {
    private val divisor = list.map { it.testDivisibleBy.toLong() }.reduce(Long::times)

    fun interact(times: Int, reduceWorryLevel: Boolean = true) {
        repeat(times) {
            for (monkey in list) {
                for (currentWorryLevel in monkey.items) {
                    val target: Long =
                        if (monkey.worryLevelOperationValue == "old") currentWorryLevel else monkey.worryLevelOperationValue.toLong()
                    val newWorryLevel = if (reduceWorryLevel) calculateReducedWorryLevel(
                        monkey, currentWorryLevel, target
                    ) else calculateSelfPacedWorryLevel(monkey, currentWorryLevel, target)
                    list[if (newWorryLevel % monkey.testDivisibleBy == 0L) monkey.ifTrueThrowToMonkey else monkey.ifFalseThrowToMonkey].items.add(
                        newWorryLevel
                    )
                    monkey.inspected += 1
                }
                monkey.items.clear()
            }
        }
    }

    private fun calculateSelfPacedWorryLevel(monkey: Monkey, itemWorryLevel: Long, target: Long): Long {
        return calculateWorryLevel(monkey.worryLevelOperation, itemWorryLevel, target) % this.divisor
    }

    private fun calculateReducedWorryLevel(monkey: Monkey, itemWorryLevel: Long, target: Long): Long {
        return calculateWorryLevel(monkey.worryLevelOperation, itemWorryLevel, target) / 3L
    }

    private fun calculateWorryLevel(worryLevelOperation: String, itemWorryLevel: Long, target: Long): Long {
        return when (worryLevelOperation) {
            "*" -> itemWorryLevel * target
            "/" -> itemWorryLevel / target
            "+" -> itemWorryLevel + target
            "-" -> itemWorryLevel - target
            else -> error("Unexpected operation")
        }
    }

    companion object {
        fun create(listOfMonkeys: Array<Monkey>): Monkeys {
            return Monkeys(listOfMonkeys)
        }
    }
}

private fun List<String>.ofMonkeys(): Monkeys {
    return Monkeys.create(this.map { Monkey.from(it) }.toTypedArray())
}

fun main() {
    fun readInput(name: String) = File("src", "$name.txt").readText().split("\n\n")

    fun part1(input: List<String>): Long {
        val monkeys = input.ofMonkeys()
        monkeys.interact(20)
        return monkeys.list.sortedBy { it.inspected }.map { it.inspected }.takeLast(2).fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.ofMonkeys()
        monkeys.interact(10000, false)
        println(monkeys.list.sortedBy { it.inspected }.map { it.inspected }.takeLast(2))
        return monkeys.list.sortedBy { it.inspected }.map { it.inspected }.takeLast(2).fold(1) { acc, i -> acc * i }
    }

    val testInput = readInput("Day11_test")
    println("Test 1 - ${part1(testInput)}")
    check(part1(testInput) == 10605.toLong())
    println("Test 2 - ${part2(testInput)}")
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}


