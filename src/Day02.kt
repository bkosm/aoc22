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

fun main() {
    fun mapInput(input: List<String>) = input.map {
        val (first, second) = it.split(" ")
        codeMapping[first]!! to codeMapping[second]!!
    }

    fun mapScores(opponent: Shape, mine: Shape) = when (opponent) {
        mine -> drawBonus + mine.score
        beatsMapping[mine] -> winBonus + mine.score
        else -> mine.score
    }

    fun do1(arg: Collection<Pair<Shape, Shape>>) = arg.sumOf { (opponent, mine) ->
        mapScores(opponent, mine)
    }

    fun do2(arg: Collection<Pair<Shape, Shape>>) = arg.sumOf { (opponent, mine) ->
        val adjustedPick = when (mine) {
            Rock -> beatsMapping[opponent]!! // X
            Paper -> opponent // Y
            Scissors -> beatsMapping.firstNotNullOf { if (it.value == Scissors) it.key else null } // Z
        }

        mapScores(opponent, adjustedPick)
    }

    // FTW, fast and understandable. Fck enterprisy code.
    // @ClouddJR
    fun cheat2(input: List<String>) = input.sumOf {
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

    mapInput(readInput("Day02_test")).let {
        do1(it).check { this == 15 }
        do2(it).check { this == 12 }
    }

    val raw = readInput("Day02")
    mapInput(raw).let {
        do1(it).p1()
        do2(it).p2()
        cheat2(raw).p2()
    }
}
