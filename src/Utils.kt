import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "data/$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> T.check(block: T.() -> Boolean) = let { check(it.block()) { "Actual: $it" } }

fun <T> T.p1() = println("Part 1: [$this]")
fun <T> T.p2() = println("Part 2: [$this]")
fun <T> T.p(): T = also { println("p($this)") }
fun <T, Y> T.p(selector: T.() -> Y): T = also { println("p { ${selector()} }") }

fun <T> T.left() = Success(this)
fun <T> T.right() = Failure(this)
