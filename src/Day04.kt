object Day04 : DailyRunner<Int, Int> {

    private fun toRangePair(string: String) = string.split(",").map { range ->
        val (from, to) = range.split("-").map { number ->
            number.toInt()
        }
        from..to
    }.let {
        val (first, second) = it
        first to second
    }

    override fun do1(input: List<String>, isTest: Boolean): Int = input
        .map { toRangePair(it) }
        .count { (first, second) ->
            first.isContainedIn(second) or second.isContainedIn(first)
        }

    override fun do2(input: List<String>, isTest: Boolean): Int = input
        .map { toRangePair(it) }
        .count { (first, second) ->
            first.containsAny(second) or second.containsAny(first)
        }
}

fun main() {
    Day04.run()
}
