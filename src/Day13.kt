import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.io.File

class SignalPacket(val data: JsonArray) : Comparable<SignalPacket> {
    private fun compare(a: JsonElement, b: JsonElement): Int {
        when (Pair(a !is JsonArray, b !is JsonArray)) {
            true to true -> {
                return a.jsonPrimitive.content.toInt().compareTo(b.jsonPrimitive.content.toInt())
            }

            false to false -> {
                var i = 0
                while (i < (a as JsonArray).size && i < (b as JsonArray).size) {
                    val result = compare(a[i], b[i])
                    if (result == 1) return 1
                    if (result == -1) return -1

                    i += 1
                }

                if (a.size > (b as JsonArray).size) return 1
                if (a.size < b.size) return -1

                return 0
            }

            false to true -> {
                return compare(a, JsonArray(listOf(b)))
            }

            true to false -> {
                return compare(JsonArray(listOf(a)), b as JsonArray)
            }
        }

        error("Unexpected state")
    }

    override fun compareTo(other: SignalPacket): Int {
        return compare(this.data, other.data)
    }

    companion object {
        fun fromInput(item: String): SignalPacket {
            return SignalPacket(Json.decodeFromString(item))
        }
    }
}

fun main() {
    fun readPairedInput(name: String) = File("src", "$name.txt").readText().split("\n\n")

    fun readMergedInput(name: String) = File("src", "$name.txt").readText().split("\n").filter { it.length > 0 }

    fun List<String>.toPairs() = this.map { pairString ->
        val split = pairString.split("\n")
        Pair(SignalPacket.fromInput(split.first()), SignalPacket.fromInput(split.last()))
    }

    fun List<String>.toPackages() = this.map { pairString -> SignalPacket.fromInput(pairString) }

    fun part1(input: List<String>): Int {
        var result = 0
        for ((i, v) in input.toPairs().withIndex()) {
            if (v.first > v.second) continue
            result += i + 1
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val packages = input.toPackages().toMutableList()
        packages.add(SignalPacket.fromInput("[[2]]"))
        packages.add(SignalPacket.fromInput("[[6]]"))
        val indexes = mutableListOf<Int>()
        for ((i, v) in packages.sorted().withIndex()) {
            if (v.data.toString() == "[[2]]" || v.data.toString() == "[[6]]") indexes.add(i + 1)
        }
        return indexes.reduce(Int::times)
    }

    val testInput = readPairedInput("Day13_test")
    val testInput2 = readMergedInput("Day13_test")
    println("Test 1 - ${part1(testInput)}")
    println("Test 2 - ${part2(testInput2)}")
    println()
    val input = readPairedInput("Day13")
    val input2 = readMergedInput("Day13")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input2)}")
}


