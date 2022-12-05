sealed interface DailyRunner<RETURN1, RETURN2> {
    val input: List<String> get() = readInput("${this::class.simpleName}")
    val testInput: List<String> get() = readInput("${this::class.simpleName}_test")

    fun do1(input: List<String>, isTest: Boolean): RETURN1
    fun do2(input: List<String>, isTest: Boolean): RETURN2

    fun run() {
        println("=".repeat(30))
        println("<${this::class.simpleName}>")
        println("Part 1:")
        println("Test: <${do1(testInput, true)}>")
        println("Real: <${do1(input, false)}>")
        println("Part 2:")
        println("Test: <${do2(testInput, true)}>")
        println("Real: <${do2(input, false)}>")
    }

    sealed interface Ignore
}

fun main() {
    val ignoredClassNames = DailyRunner.Ignore::class.sealedSubclasses.map { it.simpleName!! }
    DailyRunner::class.sealedSubclasses.forEach { daily ->
        if (ignoredClassNames.contains(daily.simpleName).not()) {
            daily.objectInstance!!.run()
        }
    }
}
