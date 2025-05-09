package patterns.chainOfResponsibility

fun main() {
    val bryan: Handler = Director()
    val crystal: Handler = VP()
    val jeff: Handler = CEO()

    bryan.setSuccessor(crystal)
    crystal.setSuccessor(jeff)

    val request = Request(requestType = RequestType.CONFERENCE, 500)
    bryan.handleRequest(request)

    bryan.handleRequest(Request(RequestType.PURCHASE, 1000))

    bryan.handleRequest(Request(RequestType.PURCHASE, 2000))
}