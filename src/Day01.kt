fun main() {
    fun mapInput(input: List<String>): MutableMap<Int, Int> {
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

        return map
    }

    fun do1(map: Map<Int, Int>) = map.maxOf { it.value }
    fun do2(map: Map<Int, Int>) = map.values.sortedDescending().slice(0..2).sum()

    with(mapInput(readInput("Day01_test"))) {
        do1(this).check { this == 24000 }
        do2(this).check { this == 45000 }
    }

    with(mapInput(readInput("Day01"))) {
        println(do1(this))
        println(do2(this))
    }
}
