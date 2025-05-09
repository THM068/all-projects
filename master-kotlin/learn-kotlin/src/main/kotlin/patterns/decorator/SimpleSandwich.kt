package patterns.decorator

class SimpleSandwich : Sandwich {

    override fun make(): String {
        return "bread"
    }
}