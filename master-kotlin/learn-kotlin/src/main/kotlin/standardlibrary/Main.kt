package standardlibrary

import org.joda.time.DateTime
import org.joda.time.ReadableDateTime

fun main() {
    createName(Name(name = "Thomas"))

    val p = Person("Kerrie", 34)
    p.surname = "Alexandra"
    println("My surname is ${p.surname}")

    val intPredicate = IntPredicate { it % 2 == 0}

    println(intPredicate.accept(2))

    val pp = Person("Thando", DateTime())
    println("day of the week ${pp.dayOfWeek}")

}


inline class Name(val name: String) {
    val length: Int
    get() = name.length
}

fun createName(name: Name) {
    println(name.name)
    println(name.length)
}

class Person(val name: String, dateTime: ReadableDateTime) : ReadableDateTime by dateTime  {
     var surname: String = ""
     set(value) {
         field = value
     }

    init {
        println("I am an init ${name}")
    }

    constructor(name: String, age: Int): this(name, DateTime()) {
        println("I am a secondary constructor ${age}")
    }
}

fun interface IntPredicate {
    fun accept(i: Int) : Boolean
}