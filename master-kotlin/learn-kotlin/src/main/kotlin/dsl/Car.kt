package dsl

import coroutines.test

class Car(val model: String?, val year: Int) {

    override fun toString() = "${model} - ${year}"

    private constructor(builder: Builder) : this(builder.model, builder.year)
    companion object {
       inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var model: String? = null
        var year: Int = 0

        fun build() = Car(this)
    }
}

fun main() {
    val car = Car.build {
        model = "Mazda 322"
        year = 1992
    }


}