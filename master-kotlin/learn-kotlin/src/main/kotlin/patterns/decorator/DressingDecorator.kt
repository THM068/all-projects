package patterns.decorator

class DressingDecorator( val sandwich: Sandwich): SandwichDecorator(sandwich) {

    override fun make(): String {
        return sandwich.make() + dressing()
    }

    private fun dressing() : String {
        return "dressing "
    }
}