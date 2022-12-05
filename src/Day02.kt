import Shape.Paper
import Shape.Rock
import Shape.Scissors

enum class Shape(val score: Int) {
    Rock(1), Paper(2), Scissors(3);
}

val beatsMapping = mapOf(
    Rock to Scissors,
    Paper to Rock,
    Scissors to Paper,
)

val codeMapping = mapOf(
    "A" to Rock,
    "B" to Paper,
    "C" to Scissors,
    "X" to Rock,
    "Y" to Paper,
    "Z" to Scissors,
)

const val drawBonus = 3
const val winBonus = 6

object Day02 : DailyRunner<Int, Int> {
    private fun mapInput(input: List<String>) = input.map {
        val (first, second) = it.split(" ")
        codeMapping[first]!! to codeMapping[second]!!
    }

    private fun mapScores(opponent: Shape, mine: Shape) = when (opponent) {
        mine -> drawBonus + mine.score
        beatsMapping[mine] -> winBonus + mine.score
        else -> mine.score
    }

    override fun do1(input: List<String>, isTest: Boolean): Int = mapInput(input).sumOf { (opponent, mine) ->
        mapScores(opponent, mine)
    }

    override fun do2(input: List<String>, isTest: Boolean): Int = input.sumOf {
        @Suppress("RemoveRedundantCallsOfConversionMethods")
        when (it) {
            "A X" -> 3
            "A Y" -> 4
            "A Z" -> 8
            "B X" -> 1
            "B Y" -> 5
            "B Z" -> 9
            "C X" -> 2
            "C Y" -> 6
            "C Z" -> 7
            else -> 0.toInt()
        }
    }
}

fun main() {
    Day02.run()
}
