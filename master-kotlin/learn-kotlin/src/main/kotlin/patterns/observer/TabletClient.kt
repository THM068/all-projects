package patterns.observer

class TabletClient(val subject: Subject): Observer() {

    init {
        this.subject.attach(this)
    }

    fun addMessage(message: String) {
        subject.setState("${message} sent from Tablet")
    }
    override fun update() {
        println("Tablet Stream: ${subject.getState()}")
    }
}