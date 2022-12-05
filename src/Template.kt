object Template : DailyRunner<Int, Int>, DailyRunner.Ignore {
    override fun do1(input: List<String>) = 1
    override fun do2(input: List<String>) = 1
}

fun main() {
    Template.run()
}
