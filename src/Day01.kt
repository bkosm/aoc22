object Day01 : DailyRunner<Int, Int> {
    private fun mapInput(input: List<String>): Collection<Int> {
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

    override fun do1(input: List<String>) = mapInput(input).max()
    override fun do2(input: List<String>) = mapInput(input).sortedDescending().slice(0..2).sum()
}

fun main() {
    Day01.run()
}
