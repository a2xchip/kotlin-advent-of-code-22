class FilesystemNode(
    private val name: String,
    val parent: FilesystemNode?,
) {
    val children: MutableList<FilesystemNode> = mutableListOf()
    val fileSizes: MutableList<Int> = mutableListOf()
    fun getTotalSize(): Int {
        return fileSizes.fold(0) { acc, i -> acc + i } + getChildSize()
    }

    private fun getChildSize(): Int {
        return children.sumOf { it.getTotalSize() }
    }

    fun findChild(name: String): FilesystemNode {
        return children.find { it.name == name }!!
    }

    fun getRoot(): FilesystemNode {
        if (parent == null) {
            return this
        }

        return parent.getRoot()
    }

    fun listNodes(): List<FilesystemNode> {
        return all(this.getRoot())
    }

    companion object {
        fun fromInput(input: List<String>): FilesystemNode {
            var cwd: FilesystemNode? = null
            for (l in input) {
                if (l == "$ ls") continue

                if (l.startsWith("\$ cd")) {
                    val targetDir = l.split(" ").last()
                    cwd = when (targetDir) {
                        "/" -> {
                            cwd?.getRoot() ?: FilesystemNode(targetDir, null)
                        }

                        ".." -> {
                            cwd!!.parent
                        }

                        else -> {
                            cwd!!.findChild(targetDir)
                        }
                    }

                    continue
                }

                val (first, second) = l.split(" ")
                if (first == "dir") {
                    cwd!!.children.add(FilesystemNode(second, cwd))
                } else {
                    cwd!!.fileSizes.add(first.toInt())
                }
            }

            return cwd!!.getRoot()
        }

        fun all(node: FilesystemNode?): List<FilesystemNode> {
            if (node == null) {
                return mutableListOf()
            }
            val list = mutableListOf(node)
            for (c in node.children) {
                list += this.all(c)
            }

            return list
        }

        fun findNodesSmallerThan(amount: Int, node: FilesystemNode?): List<FilesystemNode> {
            val list = mutableListOf<FilesystemNode>()
            if (node == null) {
                return mutableListOf()
            }

            if (node.getTotalSize() <= amount) {
                list.add(node)
            }

            for (c in node.children) {
                list += this.findNodesSmallerThan(amount, c)
            }

            return list
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val rootNode = FilesystemNode.fromInput(input)
        return FilesystemNode.findNodesSmallerThan(100000, rootNode).sumOf { it.getTotalSize() }
    }

    fun part2(input: List<String>): Int {
        val rootNode = FilesystemNode.fromInput(input)
        val diskCapacity = 70000000
        val needSpace = 30000000
        val diskSpaceLeft = diskCapacity - rootNode.getTotalSize()
        val amountOfAdditionalSpaceRequired = needSpace - diskSpaceLeft
        return rootNode.listNodes().map { it.getTotalSize() }.filter { it >= amountOfAdditionalSpaceRequired }
            .minByOrNull { it }!!
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
    check(part1(input) == 2031851)
    check(part2(input) == 2568781)
}

