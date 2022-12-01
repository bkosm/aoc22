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

        return map.maxBy { it.value }.key
    }

    val testInput = readInput("Day01_test")
    val realInput = readInput("Day01")

    check(doIt(testInput) == 4)
    println(doIt(realInput))
}
