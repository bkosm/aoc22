import kotlin.math.roundToInt

object Day03 : DailyRunner<Int, Int> {
    val PriorityMap = ((('a'..'z') zip (1..26)) + (('A'..'Z') zip (27..52))).toMap()

    fun common(a: String, b: String) = a.toSet().intersect(b.toSet())

    override fun do1(input: List<String>) = input.sumOf { line ->
        val aSize = (line.length / 2.0).roundToInt()
        val bSize = line.length - aSize

        val a = line.dropLast(bSize)
        val b = line.drop(aSize)

        common(a, b).sumOf { PriorityMap[it]!! }
    }

    override fun do2(input: List<String>) = 1
}

fun main() {
    Day03.run()
}