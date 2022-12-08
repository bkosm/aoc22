typealias File = Pair<String, Int>

val List<File>.totalSize: Int get() = sumOf { it.second }

class ConsoleParser {
    private val pathToFiles = mutableMapOf<String, MutableList<File>>()
    private val childLinks = mutableMapOf<String, MutableSet<String>>()
    private var currentPath = ""

    val directories: List<String> get() = pathToFiles.keys.toList()

    fun process(line: String) {
        CD.matchEntire(line)?.let {
            val cmd = it.groups[1]!!.value
            doCD(cmd)
        } ?: FILE.matchEntire(line)?.let { match ->
            val sizeString = match.groups[1]!!.value
            val name = match.groups[2]!!.value
            doLSOnFile(name, sizeString)
        }
    }

    private fun doLSOnFile(name: String, sizeString: String) = pathToFiles[currentPath]?.let {
        pathToFiles[currentPath]!!.add(File(name, sizeString.toInt()))
    } ?: run {
        pathToFiles.put(currentPath, mutableListOf(File(name, sizeString.toInt())))
    }

    private fun doCD(cmd: String) {
        currentPath = when {
            cmd.startsWith("/") -> cmd
            cmd == ".." -> currentPath.pathOperation { dropLast(1) }
            else -> currentPath.pathOperation { this + cmd }
        }

        val (current, parent) = currentPath.pathElements().run {
            when (size) {
                0 -> "/" to null
                1 -> currentPath to "/"
                else -> currentPath to "/" + dropLast(1).joinToString("/")
            }
        }

        parent?.let { _ ->
            childLinks[parent]?.let {
                childLinks[parent]!!.add(current)
            } ?: run {
                childLinks[parent] = mutableSetOf(current)
            }
        }
    }

    fun totalSizeOf(dir: String): Int {
        val fileSize = pathToFiles[dir]?.totalSize ?: 0
        val children = childLinks[dir] ?: mutableSetOf()

        return fileSize + children.sumOf { totalSizeOf(it) }
    }

    private fun String.pathElements() = split("/").filter { it.isNotBlank() }
    private fun String.pathOperation(mapper: List<String>.() -> List<String>) =
        runCatching {
            "/" + pathElements().mapper().joinToString("/")
        }.fold(
            onSuccess = { it },
            onFailure = { "/" }
        )

    companion object {
        private val CD = "^\\\$ cd (.*)\$".toRegex()
        private val FILE = "^(\\d*) (.*)\$".toRegex()
        // private val DIR = "^dir (.*)\$".toRegex()
    }
}

object Day07 : DailyRunner<Int, Int> {
    override fun do1(input: List<String>, isTest: Boolean) = ConsoleParser().run {
        input.forEach { process(it) }

        directories.map { totalSizeOf(it) }.filter { it <= 100_000 }.sum()
    }

    override fun do2(input: List<String>, isTest: Boolean): Int = 1
}

fun main() {
    Day07.run()
}
