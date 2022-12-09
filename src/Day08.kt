typealias Id = Int
typealias Height = Int
typealias Tree = Pair<Height, Id>
typealias TreeRow = List<Tree>

fun toTreeMatrixFlat(input: List<String>): List<TreeRow> {
    var lastId = Int.MAX_VALUE

    val horizontal = input.map { line ->
        line.split("").filter(String::isNotBlank).map { Tree(it.toInt(), lastId--) }
    }

    val vertical = mutableListOf<TreeRow>().also {
        val height = input.size
        val width = input.first().length
        for (i in 0 until width) {
            val column = mutableListOf<Tree>()
            for (j in 0 until height) {
                column.add(horizontal[j][i])
            }
            it.add(column)
        }
    }

    return horizontal + vertical
}

object Day08 : DailyRunner<Int, Int> {
    override fun do1(input: List<String>, isTest: Boolean): Int {
        val rows = toTreeMatrixFlat(input)

        val allVisible = rows.map { visibleFrom(it) + visibleFrom(it.reversed()) }

        return allVisible.flatten().toSet().count()
    }

    private fun visibleFrom(row: TreeRow): List<Tree> = mutableListOf(row.first()).apply {
        var lastVisible = row.first()

        row.drop(1).forEach {
            if (it.first > lastVisible.first) {
                lastVisible = it
                add(it)
            }
        }
    }

    override fun do2(input: List<String>, isTest: Boolean): Int = 1
}

fun main() {
    Day08.run()
}
