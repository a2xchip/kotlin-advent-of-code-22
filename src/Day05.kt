import java.io.File

class Stacks(val stacks: List<ArrayDeque<Char>>) {
    fun move(action: Action) {
        repeat(action.times) {
            val c = stacks[action.from].removeFirst()
            stacks[action.to].addFirst(c)
        }
    }

    fun moveBatched(action: Action) {
        val batch = mutableListOf<Char>()
        repeat(action.times) {
            batch.add(stacks[action.from].removeFirst())
        }

        for (i in batch.reversed()) {
            stacks[action.to].addFirst(i)
        }
    }

    fun getCreatesOnTop(): String {
        val resultList = mutableListOf<Char>()
        for (s in this.stacks) {
            if (s.isEmpty()) continue
            resultList.add(s.first())
        }
        return resultList.joinToString("")
    }

    companion object {
        fun create(input: String): Stacks {
            val rows = input.split("\n").dropLast(1)
            val crateRows = rows.slice(0..rows.lastIndex).reversed()
            val numberOfColumns = rows.maxOf { it.length }
            val indices = (0..numberOfColumns step 4).map { it + 1 }
            val listOfStacks = MutableList(numberOfColumns) { ArrayDeque<Char>() }

            for ((k, position) in indices.withIndex()) {
                for (row in crateRows) {
                    if (row.length - 1 > position && row[position].isLetter()) listOfStacks[k].addFirst(row[position])
                }
            }

            return Stacks(listOfStacks)
        }
    }
}

data class Action(val from: Int, val to: Int, val times: Int) {
    companion object {
        fun from(line: String): Action {
            val lineSplit = line.split(" ")
            return Action(lineSplit[3].toInt() - 1, lineSplit[5].toInt() - 1, lineSplit[1].toInt())
        }
    }
}

fun main() {
    fun readInput(name: String) = File("src", "$name.txt").readText().split("\n\n")

    fun part1(input: List<String>): String {
        val (stacksString, listOfActions) = input
        val stacks = Stacks.create(stacksString)
        listOfActions.split("\n").forEach { stacks.move(Action.from(it)) }

        return stacks.getCreatesOnTop()
    }

    fun part2(input: List<String>): String {
        val (stacksString, moves) = input
        val stacks = Stacks.create(stacksString)

        moves.split("\n").forEach {
            stacks.moveBatched(Action.from(it))
        }

        return stacks.getCreatesOnTop()
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")
    println("Test 1 - ${part1(testInput)}")
    println("Test 2 - ${part2(testInput)}")

    val input = readInput("Day05")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == "QMBMJDFTD")
    check(part2(input) == "NBTVTJNFJ")
}

