import dev.forkhandles.result4k.partition
import kotlin.math.roundToInt

object Day03 : DailyRunner<Int, Int> {
    @JvmInline
    private value class Prio private constructor(val value: Int) {
        companion object {
            private val PriorityMap = ((('a'..'z') zip (1..26)) + (('A'..'Z') zip (27..52))).toMap()
            fun of(char: Char) = Prio(PriorityMap[char]!!)
        }
    }

    private fun common(a: String, b: String) = a.toSet().intersect(b.toSet())
    private fun common(a: String, b: String, c: String) = common(a, b).joinToString("").let { common(it, c) }

    override fun do1(input: List<String>, isTest: Boolean): Int = input.sumOf { line ->
        val aSize = (line.length / 2.0).roundToInt()
        val bSize = line.length - aSize

        val a = line.dropLast(bSize)
        val b = line.drop(aSize)

        common(a, b).sumOf { Prio.of(it).value }
    }

    override fun do2(input: List<String>, isTest: Boolean): Int = input.windowed(3, 3).map { elves ->
        val (a, b, c) = elves

        common(a, b, c).run {
            if (size == 1) Prio.of(first()).left()
            else map { Prio.of(it) }.right()
        }
    }.partition().run {
        val certain = first.sumOf { it.value }

        val pool = second.flatten().toMutableSet()
        val fitted = second.sumOf { possiblePrios ->
            possiblePrios.random().also {
                check(pool.remove(it)) { "Pick $it was already missing in the pool" }
            }.value
        }

        certain + fitted
    }
}

fun main() {
    Day03.run()
}
