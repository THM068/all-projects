package patterns.chainOfResponsibility

open abstract class Handler {
    protected var successor: Handler? = null

    abstract fun handleRequest(request: Request)

    @JvmName("setSuccessor1")
    fun setSuccessor(handler: Handler) {
        this.successor = handler
    }

}