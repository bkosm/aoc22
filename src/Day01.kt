fun main() {
    fun doIt(input: List<String>): Int {
        var elf = 1
        val map = mutableMapOf(elf to 0)

        input.forEach {
            if (it.isBlank()) {
                elf++
                map[elf] = 0
            } else {
                map[elf] = map[elf]!! + it.toInt()
            }
        }

        return map.maxOf { it.value }
    }

    val testInput = readInput("Day01_test")
    val realInput = readInput("Day01")

    doIt(testInput).let {
        check(it == 24000) { "Actual: $it" }
    }

    println(doIt(realInput))
}
