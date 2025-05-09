package coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = GlobalScope.launch {
        delay(1000L)
        println("hello")
    }
    println("world")
    job.join()
}