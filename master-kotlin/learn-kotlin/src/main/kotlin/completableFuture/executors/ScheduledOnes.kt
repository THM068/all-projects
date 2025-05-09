package completableFuture.executors

import java.sql.Time
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
    val service = Executors.newScheduledThreadPool(10)
    service.schedule({
        println("I will run after 3 seconds on ${Thread.currentThread()}")
    }, 3, TimeUnit.SECONDS)

    service.scheduleAtFixedRate({
        println("I will run every 10 seconds with an initial delay of 5 seconds")
    }, 5, 10, TimeUnit.SECONDS)


    service.scheduleWithFixedDelay({
        println("I will run every 11 seconds with an initial delay of 6 seconds")
    }, 6, 11, TimeUnit.SECONDS)
}