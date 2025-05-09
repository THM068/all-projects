package patterns.decorator

class MeatDecorator(val sandwich: Sandwich): SandwichDecorator(sandwich) {

    override fun make():String {
        return sandwich.make() + withMeat()
    }

    private fun withMeat(): String {
        return " Meat "
    }
}