package dsl

fun main() {
  val names = buildString {
      append("Hello\n")
      append("World\n")
      append("Thando")
  }
  println(names)

  val run: (Int,Int) -> Int = { x, y -> x + y }

  val namePresent: (String, String) -> Boolean = { x,y -> x.isNotBlank() && y.isNotBlank()}

  println(run(2,3))

  println(CreateProfile(isPresent = ::_namePresent).isPresent("thandi","love"))
  println(CreateProfile(isPresent = namePresent).isPresent("thandi",""))
}

fun buildString(action: StringBuilder.() -> Unit): String {
    val sb = java.lang.StringBuilder()
    sb.action()
    return  sb.toString()
}

fun _namePresent(x: String, y: String) = x.isNotBlank() && y.isNotBlank()

data class CreateProfile(val isPresent: (String,String) -> Boolean)