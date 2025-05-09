package patterns.observer

class PhoneClient(val subject: Subject): Observer() {

    init {
       subject.attach(this)
    }

    fun addMessage(message: String) {
        subject.setState("${message} sent from Phone")
    }
    override fun update() {
        println("Phone Stream: ${subject.getState()}")
    }
}