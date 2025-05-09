package coroutines

import arrow.core.Either
import kotlinx.coroutines.*
import java.io.File

fun main() = runBlocking {
    GlobalScope.launch {
       val x = async { makeSureYourLogicDoesNotHaveSideEffects() }.await()

       when(x) {
           is Either.Left -> println(x.value)
           is Either.Right -> println(x.value)
       }
     }
    delay(2000L)
}

suspend fun test() {
    val f = File("/Users/tma24/test.txt")
    repeat(100) {
        f.appendText("Hello world .... ${it} \n")
        delay(500L)
    }
}

fun potentialThrowingCode(): String = throw RuntimeException("Blow up!")

suspend fun makeSureYourLogicDoesNotHaveSideEffects(): Either<Error, String> =
    Either.catch { potentialThrowingCode() }.mapLeft { Error(it) }