package completableFuture

import java.util.concurrent.CompletableFuture

fun main() {
    println(Thread.currentThread())
    val completableFuture = CompletableFuture.supplyAsync { compute() }. thenApply { it * 3 }.thenAccept {
        println("Hello ${it}")
    }.thenRun {
        println("all done")
    }.thenRun {
        println("psyche got you")
    }

    //completableFuture.get()

}

fun compute() : Int {
    println(Thread.currentThread())
    return 2
}