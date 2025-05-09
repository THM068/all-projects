package patterns.observer

fun main() {
    val subject: Subject = MessageStream()

    val phoneClient = PhoneClient(subject)
    val tabletClient = TabletClient(subject)

    phoneClient.addMessage("Here is a new message")
    tabletClient.addMessage("Another message")


}