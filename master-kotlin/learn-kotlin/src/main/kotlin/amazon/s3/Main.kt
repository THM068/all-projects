package amazon.s3

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.computations.option
import java.io.File

suspend fun main() {
    s3{
        upload {
            bucketName = "mafela-photos"
            file = File("/Users/tma24/names")
            key = "test-1.txt"
        }
    }



   val result =  option {
        val a = Some(1).bind()
        val b = Some(1 + a).bind()
        val c = Some(1 + b).bind()
        a + b + c
    }

    println(result)

    fun t(): Option<Int> = None
}
