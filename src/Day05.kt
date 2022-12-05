typealias Crate = Char

class Ship(private val stacks: List<MutableList<Crate>>) {
    data class MoveCommand(
        val from: Int,
        val to: Int,
        val amount: Int,
    )

    fun move(command: MoveCommand, moveMultiple: Boolean = false) {
        if (moveMultiple) {
            val multiple = stacks[command.from - 1].takeLast(command.amount).onEach { _ ->
                stacks[command.from - 1].removeLastOrNull()
            }
            stacks[command.to - 1].addAll(multiple)

        } else {
            repeat(command.amount) {
                stacks[command.from - 1].removeLastOrNull()?.let {
                    stacks[command.to - 1].add(it)
                }
            }
        }
    }

    @Suppress("unused")
    fun log(cmd: Any? = null) {
        println("=== Moved $cmd")
        stacks.forEachIndexed { index, stack ->
            println("Stack ${index + 1}: ${stack.joinToString("")}")
        }
    }

    fun tops() = stacks.map { it.lastOrNull() ?: " " }.joinToString("")
}

fun read(input: List<String>, columns: Int): Pair<List<MutableList<Crate>>, List<Ship.MoveCommand>> {
    val setupLine = "^${(0 until columns).joinToString("\\ ") { "(\\ {3}|\\[[A-Z]\\])" }}\$".toRegex()
    val moveLine = "^move (\\d*) from (\\d*) to (\\d*)\$".toRegex()

    val commands = mutableListOf<Ship.MoveCommand>()
    val stacks = mutableListOf<MutableList<Crate>>()

    input.forEach { raw ->
        val line = raw.trim('|')
        setupLine.matchEntire(line)?.let { match ->
            val values = match.groupValues.drop(1)
            if (stacks.isEmpty()) {
                values.forEach {
                    if (it.isBlank().not()) stacks.add(mutableListOf(it[1]))
                    else stacks.add(mutableListOf())
                }
            } else {
                values.forEachIndexed { i, e ->
                    if (e.isBlank().not()) stacks[i].add(e[1])
                }
            }
        }
        moveLine.matchEntire(line)?.let {
            commands.add(
                Ship.MoveCommand(
                    from = it.groups[2]!!.value.toInt(),
                    to = it.groups[3]!!.value.toInt(),
                    amount = it.groups[1]!!.value.toInt(),
                )
            )
        }
    }

    return stacks.map { it.reversed().toMutableList() } to commands
}


object Day05 : DailyRunner<String, String> {
    override fun do1(input: List<String>, isTest: Boolean) =
        read(input, if (isTest) 3 else 9).let { (stacks, commands) ->
            Ship(stacks).apply {
                if (isTest) log()

                commands.forEach { move(it); if (isTest) log(it) }
            }.tops()
        }

    override fun do2(input: List<String>, isTest: Boolean) =
        read(input, if (isTest) 3 else 9).let { (stacks, commands) ->
            Ship(stacks).apply {
                if (isTest) log("initial")

                commands.forEach { move(it, moveMultiple = true); if (isTest) log(it) }
            }.tops()
        }
}

fun main() {
    Day05.run()
}
