package completableFuture

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

fun main() {
//   val completableFuture = CompletableFuture<Int>()
//   //completableFuture.completeOnTimeout(500, 3, TimeUnit.SECONDS)
//    completableFuture.orTimeout(3, TimeUnit.SECONDS)
//    process(completableFuture)
//
//   completableFuture.complete(5)
}

fun process(future: CompletableFuture<Int> ) {
    future.thenApply { data -> data * 2 }
        .thenAccept(System.out::println)
}