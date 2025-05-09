package patterns.chainOfResponsibility

class Director: Handler() {

    override fun handleRequest(request: Request) {
        if(request.requestType == RequestType.CONFERENCE) {
            println("Directors can approve conference")
        }
        else {
            successor?.handleRequest(request)
        }
    }
}