fun main() {
    fun evaluateGame(game: String): Int {
//        A Rock,
//        B Paper
//        C Scissors
//        X for Rock
//        Y for Paper
//        Z for Scissors
//        i.e
//        (A Z) Rock defeats Scissors
//        (B X) Paper defeats Rock
//        (C Y) Scissors defeats Paper
//        ABC
//        XYZ
        return when (game) {
            "A Y" -> 6
            "B Z" -> 6
            "C X" -> 6
            "A X" -> 3
            "B Y" -> 3
            "C Z" -> 3
            "A Z" -> 0
            "B X" -> 0
            "C Y" -> 0
            else -> throw Exception("Undefined game's state")
        }
    }

    fun evaluateChoice(choice: String): Int {
        //  1 for Rock, 2 for Paper, and 3 for Scissors
        return when (choice) {
            "X" -> 1
            "Y" -> 2
            "Z" -> 3
            else -> throw Exception("Unexpected choice")
        }
    }

    fun guessChoice(opponentTurnGameEvaluation: Pair<String, Int>): String {
        return when (opponentTurnGameEvaluation) {
            Pair("A", 6) -> "Y"
            Pair("B", 6) -> "Z"
            Pair("C", 6) -> "X"
            Pair("A", 3) -> "X"
            Pair("B", 3) -> "Y"
            Pair("C", 3) -> "Z"
            Pair("A", 0) -> "Z"
            Pair("B", 0) -> "X"
            Pair("C", 0) -> "Y"
            else -> throw Exception("Unexpected guess")
        }
    }


    fun matchAction(letter: String): Int {
        //    X means you need to lose,
        //    Y means you need to end the round in a draw, and
        //    Z means you need to win
        return when (letter) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> throw Exception("HELL")
        }
    }

    // "A X" "X"
    fun calculateScore(game: String) = evaluateGame(game) + evaluateChoice(game.last().toString())

    fun part1(input: List<String>): Int {
        return input.sumOf(::calculateScore)
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { turn ->
            val (opponent, actionLetter) = turn.split(" ")
            val choice = guessChoice(Pair(opponent, matchAction(actionLetter)))
            val game = "$opponent $choice"

            calculateScore(game)
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println("Part 1 - ${part1(input)}")
    println("Part 2 - ${part2(input)}")
}