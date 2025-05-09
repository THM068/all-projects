package standardlibrary

fun main() {
    val myclass = Myclass.create()
    assert(myclass.name == "Thando")

    Myclass.myPrint()
}

class Myclass() {
    val name = "Thando"
    companion object  {
        fun create() : Myclass = Myclass()
    }
}

fun Myclass.Companion.myPrint() {

        println("myPrint me")
}