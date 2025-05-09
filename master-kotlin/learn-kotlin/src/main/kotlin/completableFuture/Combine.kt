package completableFuture

import java.util.concurrent.CompletableFuture

fun main() {
    create(2).thenCombine( create(3), { a,b ->  a + b })
        .thenAccept(System.out::println)
}

fun create(n: Int) = CompletableFuture.supplyAsync { n }