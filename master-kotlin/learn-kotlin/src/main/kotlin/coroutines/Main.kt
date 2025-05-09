package coroutines

import kotlinx.coroutines.*

 fun main() = runBlocking {
     GlobalScope.launch {
         delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
         println("World!")
     }
     println("Hello,") // main thread continues while coroutine is delayed
     delay(2000L)
 }


suspend fun getName(): String {
    delay(5000)
    return "THANDO"
}