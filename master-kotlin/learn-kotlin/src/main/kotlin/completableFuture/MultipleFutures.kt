package completableFuture

import kotlinx.coroutines.future.await
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.sound.midi.Sequence
import java.util.concurrent.*
import kotlin.coroutines.*
suspend fun main() {
    println("Main.... ${Thread.currentThread().name}")
    val numberOfCores = Runtime.getRuntime().availableProcessors()
    val service: ExecutorService = Executors.newFixedThreadPool(numberOfCores)
    service.execute {
        println("Hello world .... ${Thread.currentThread().name}")
    }
    println("The number of cores ${numberOfCores}")
    val f1 = CompletableFuture.supplyAsync { "Hello "}.await()
    val f2 = CompletableFuture.supplyAsync { "world "}.await()
    val f3 = CompletableFuture.supplyAsync { " mars baby"}.await()
    println(" ${f1} ${f2} ${f3}")


}