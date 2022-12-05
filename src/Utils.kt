@file:Suppress("unused")

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.flatMapFailure
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.mapFailure
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


// wrappers for Result4k to make them more versatile
fun <T> T.left() = Success(this)
fun <T> T.right() = Failure(this)
fun <R, R1, L, L1> Result<L, R>.mapLeft(block: (L) -> L1) = map(block)
fun <R, R1, L, L1> Result<L, R>.mapRight(block: (R) -> R1) = mapFailure(block)
fun <R, R1, L, L1> Result<L, R>.flatMapLeft(block: (L) -> Result<R1, L1>) = flatMap(block)
fun <R, R1, L, L1> Result<L, R>.flatMapRight(block: (R) -> Result<R1, L1>) = flatMapFailure(block)

@OptIn(ExperimentalStdlibApi::class)
fun <T : Comparable<T>> OpenEndRange<T>.isContainedIn(other: OpenEndRange<T>) = when {
    start >= other.start && endExclusive <= other.endExclusive -> true
    else -> false
}
