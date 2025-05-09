package patterns.command

class Light {
    var isOn = false

    fun toggle() {
        if(isOn) {
            off()
            isOn = false
        }
        else {
            on()
            isOn = true
        }
    }
    fun on(): Unit {
        println("Light switched on")
    }

    fun off(): Unit {
        println("Light switched off")
    }
}