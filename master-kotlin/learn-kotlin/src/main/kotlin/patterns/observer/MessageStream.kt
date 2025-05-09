package patterns.observer

import java.util.*

class MessageStream: Subject() {

    val messageHistory: Deque<String> = ArrayDeque<String>()

    override fun getState(): String {
        return messageHistory.last
    }

    override fun setState(message: String) {
       this.messageHistory.add(message)
       this.notifyObservers()
    }
}