package patterns.observer

import kotlin.collections.ArrayList

abstract class Subject {

    private val observers = ArrayList<Observer>()

    abstract fun getState(): String

    abstract fun setState(state: String)

    fun attach(observer: Observer) {
        this.observers.add(observer)
    }

    fun detach(observer: Observer) {
        this.observers.remove(observer)
    }

    fun notifyObservers() {
        this.observers.forEach {
            it.update()
        }
    }
}