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

fun toHeightMatrix(input: List<String>): List<List<Height>> = input.map { line ->
    line.split("").filter(String::isNotBlank).map { it.toInt() }
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

    override fun do2(input: List<String>, isTest: Boolean): Int {
        val scores = toHeightMatrix(input).run {
            mapIndexed { j, row ->
                row.mapIndexed { i, tree ->
                    scenicScore(this, i, j, tree)
                }
            }
        }

        return scores.flatten().max()
    }

    private fun scenicScore(matrix: List<List<Height>>, x: Int, y: Int, height: Height): Int {
        var left = 0
        for (i in x - 1 downTo 0) {
            left++
            if (height <= matrix[i][y]) break
        }

        var right = 0
        for (i in x + 1 until matrix.first().size) {
            right++
            if (height <= matrix[i][y]) break
        }

        var up = 0
        for (j in y - 1 downTo 0) {
            up++
            if (height <= matrix[x][j]) break
        }

        var down = 0
        for (j in y + 1 until matrix.size) {
            down++
            if (height <= matrix[x][j]) break
        }

        return left * right * up * down
    }
}

fun main() {
    Day08.run()
}
