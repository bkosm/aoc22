fun main() {
    fun mapInput(input: List<String>): Collection<Int> {
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

        return map.values
    }

    fun do1(arg: Collection<Int>) = arg.max()
    fun do2(arg: Collection<Int>) = arg.sortedDescending().slice(0..2).sum()

    mapInput(readInput("Day01_test")).let {
        do1(it).check { this == 24000 }
        do2(it).check { this == 45000 }
    }

    mapInput(readInput("Day01")).let {
        do1(it).p1()
        do2(it).p2()
    }
}
