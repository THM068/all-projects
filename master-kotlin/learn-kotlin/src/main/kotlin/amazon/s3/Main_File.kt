package amazon.s3

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun doSomething() {
    print("doSomething start")
    doSomethingElse()
    print("doSomething end")
}

inline fun doSomethingElse() {
    print("doSomethingElse")
}

suspend fun main() {
    runBlocking {
        flows().map{
            it.toUpperCase()
        }.collect {
            println(it)
        }
    }
}

fun flows(): Flow<String> = flow {
    while(true) {
        println("******")
        emit("Thando")
        delay(1000)
        emit("Mafela")
        delay(1000)
        emit("Khulani")
    }


}