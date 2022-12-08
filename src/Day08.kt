class Forest(private val trees: List<List<Int>>) {
    fun countVisibleTrees(): Int {
        var count = 0
        for ((ir, row) in trees.withIndex()) {
            for ((ic, _) in row.withIndex()) {
                if (isVisible(ir, ic)) count += 1
            }
        }

        return count
    }

    fun topScore(): Int {
        val scores = mutableListOf<Int>()
        for ((ir, row) in trees.withIndex()) {
            for ((ic, _) in row.withIndex()) {
                if (ir == 0 || ic == 0 || ir == trees.lastIndex || ic == row.lastIndex) continue
                scores.add(calculateScore(ir, ic))
            }
        }

        return scores.max()
    }

    private fun isVisible(row: Int, column: Int): Boolean {
        val height = trees[row][column]
        return isVisibleLeft(row, column, height) ||
                isVisibleRight(row, column, height) ||
                isVisibleTop(row, column, height) ||
                isVisibleBottom(row, column, height)
    }

    private fun isEdge(row: Int, column: Int) =
        row == 0 || column == 0 || row == trees.lastIndex || column == trees.first().lastIndex

    private fun isVisibleTop(row: Int, column: Int, height: Int): Boolean {
        if (isEdge(row, column)) return true
        if (trees[row - 1][column] >= height) return false
        return isVisibleTop(row - 1, column, height)
    }

    private fun isVisibleBottom(row: Int, column: Int, height: Int): Boolean {
        if (isEdge(row, column)) return true
        if (trees[row + 1][column] >= height) return false
        return isVisibleBottom(row + 1, column, height)
    }

    private fun isVisibleLeft(row: Int, column: Int, height: Int): Boolean {
        if (isEdge(row, column)) return true
        if (trees[row][column - 1] >= height) return false
        return isVisibleLeft(row, column - 1, height)
    }

    private fun isVisibleRight(row: Int, column: Int, height: Int): Boolean {
        if (isEdge(row, column)) return true
        if (trees[row][column + 1] >= height) return false
        return isVisibleRight(row, column + 1, height)
    }

    private fun calculateScore(row: Int, column: Int): Int {
        val height = trees[row][column]
        return countTop(row, column, height) *
                countBottom(row, column, height) *
                countLeft(row, column, height) *
                countRight(row, column, height)
    }

    private fun countTop(row: Int, column: Int, height: Int, count: Int = 0): Int {
        if (row == 0) return count
        if (trees[row - 1][column] >= height) return count + 1
        return countTop(row - 1, column, height, count + 1)
    }

    private fun countBottom(row: Int, column: Int, height: Int, count: Int = 0): Int {
        if (row == trees.size - 1) return count
        if (trees[row + 1][column] >= height) return count + 1
        return countBottom(row + 1, column, height, count + 1)
    }

    private fun countLeft(row: Int, column: Int, height: Int, count: Int = 0): Int {
        if (column == 0) return count
        if (trees[row][column - 1] >= height) return count + 1
        return countLeft(row, column - 1, height, count + 1)
    }

    private fun countRight(row: Int, column: Int, height: Int, count: Int = 0): Int {
        if (column == trees.first().size - 1) return count
        if (trees[row][column + 1] >= height) return count + 1
        return countRight(row, column + 1, height, count + 1)
    }

    companion object {
        fun fromInput(input: List<String>) =
            Forest(input.map { r -> r.toCharArray().map { it.digitToInt() } })
    }
}

fun main() {
    fun part1(forest: Forest) = forest.countVisibleTrees()
    fun part2(forest: Forest) = forest.topScore()

    val testForest = Forest.fromInput(readInput("Day08_test"))
    println("Test 1 - ${part1(testForest)}")
    println("Test 2 - ${part2(testForest)}")
    check(part1(testForest) == 21)
    check(part2(testForest) == 8)
    println()
    val forest = Forest.fromInput(readInput("Day08"))
    println("Part 1 - ${part1(forest)}")
    println("Part 2 - ${part2(forest)}")
    check(part1(forest) == 1829)
    check(part2(forest) == 291840)
}
