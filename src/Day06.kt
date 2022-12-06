fun areAllUnique(chars: Collection<Char>) = chars.run {
    toSet().size == size
}

fun positionOfSignalOfSize(line: String, signalSize: Int) = line
    .toList()
    .windowed(signalSize, 1)
    .mapIndexed { i, it -> if (areAllUnique(it)) i else null }
    .filterNotNull()
    .first()
    .let { it + signalSize }

object Day06 : DailyRunner<List<Int>, List<Int>> {
    override fun do1(input: List<String>, isTest: Boolean) = input.map { line ->
        positionOfSignalOfSize(line, 4)
    }

    override fun do2(input: List<String>, isTest: Boolean) = input.map { line ->
        positionOfSignalOfSize(line, 14)
    }
}

fun main() {
    Day06.run()
}
