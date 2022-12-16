data class SquareNode(val c: Char, val value: Int, val pos: Pair<Int, Int>) {
    override fun toString(): String {
        return """position=$pos repr='$c' value=[$value]"""
    }
}

class EscalationGraph(val nodes: List<SquareNode>, val edges: Map<SquareNode, List<SquareNode>>) {
    fun shortestPath(fromEdge: SquareNode, toSymbol: Char): MutableList<SquareNode>? {
        val queue = ArrayDeque<SquareNode>()
        queue.addLast(fromEdge)
        val cameFrom = mutableMapOf<SquareNode?, SquareNode?>()
        var current: SquareNode? = null
        while (!queue.isEmpty()) {
            current = queue.removeFirst()
            if (current.c == toSymbol) break
            for (next in edges[current]!!) {
                if (next in cameFrom) continue
                queue.addLast(next)
                cameFrom[next] = current
            }
        }

        val path = mutableListOf<SquareNode>()
        if (current?.c != toSymbol) return null
        while (current != fromEdge) {
            path.add(current!!)
            current = cameFrom[current]
        }

        return path
    }

    fun findNode(c: Char) = nodes.find { it.c == c }

    companion object {
        fun from(input: List<String>): EscalationGraph {
            val table = input.map { it.toCharArray() }
            val edges = mutableMapOf<Pair<Int, Int>, SquareNode>()
            for ((i, row) in table.withIndex()) {
                for ((j, cell) in row.withIndex()) {
                    val value: Int = when (cell) {
                        'S' -> -1
                        'E' -> 'z' - 'a' + 1
                        else -> cell - 'a'
                    }
                    edges[(i to j)] = SquareNode(cell, value, i to j)
                }
            }

            val vertices = mutableMapOf<SquareNode, List<SquareNode>>()
            for ((i, row) in table.withIndex()) {
                for ((j, _) in row.withIndex()) {
                    val edge = edges[i to j]!!
                    val linkedToEdges = mutableListOf<SquareNode>()
                    if (edges.contains(i - 1 to j) && (edges[i - 1 to j]!!.value - edge.value) <= 1) {
                        linkedToEdges.add(edges[i - 1 to j]!!)
                    }

                    if (edges.contains(i + 1 to j) && (edges[i + 1 to j]!!.value - edge.value) <= 1) {
                        linkedToEdges.add(edges[i + 1 to j]!!)
                    }

                    if (edges.contains(i to j - 1) && (edges[i to j - 1]!!.value - edge.value) <= 1) {
                        linkedToEdges.add(edges[i to j - 1]!!)
                    }

                    if (edges.contains(i to j + 1) && (edges[i to j + 1]!!.value - edge.value) <= 1) {
                        linkedToEdges.add(edges[i to j + 1]!!)
                    }

                    vertices[edge] = linkedToEdges
                }
            }

            return EscalationGraph(edges.values.toList(), vertices)
        }
    }
}

fun main() {
    fun printPath(input: List<String>, path: MutableList<SquareNode>) {
        val pathPositions = path.map { it.pos }
        val red = "\u001b[31m"
        val reset = "\u001b[0m"

        for ((i, row) in input.map { it.toCharArray() }.withIndex()) {
            for ((j, cell) in row.withIndex()) {
                if (i to j in pathPositions) {
                    print(red + cell + reset)
                } else {
                    print(cell)
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val graph = EscalationGraph.from(input)
        val path = graph.shortestPath(graph.findNode('S')!!, 'E')!!
        printPath(input, path)
        return path.size - 2
    }


    fun part2(input: List<String>): Int {
        val graph = EscalationGraph.from(input)
        val shortestPath = graph.nodes.filter { it.c == 'a' }.map { edge ->
            graph.shortestPath(edge, 'E')
        }.filter { path -> path != null && path.size > 0 }.minByOrNull { it!!.size }!!
        printPath(input, shortestPath)
        return shortestPath.size - 2
    }

    val testInput = readInput("Day12_test")
    println("Test 1 - ${part1(testInput)}")
    println("Test 2 - ${part2(testInput)}")
    println()
    val input = readInput("Day12")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}